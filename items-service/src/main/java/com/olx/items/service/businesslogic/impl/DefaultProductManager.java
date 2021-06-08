package com.olx.items.service.businesslogic.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.olx.items.service.businesslogic.CategoryManager;
import com.olx.items.service.businesslogic.ProductManager;
import com.olx.items.service.models.Category;
import com.olx.items.service.models.Product;
import com.olx.items.service.repositories.ProductRepository;

@Component
public class DefaultProductManager implements ProductManager {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryManager categoryManager;
	
	@Autowired
	private RestTemplate restTemplate;
		
	@Override
	public List<Product> getAllProducts() {
		List<Product> result = new ArrayList<Product>();
		for(Product p: productRepository.findAll())
			result.add(p);
		return result;
	}

	@Override
	public Product save(Product product) {
		
		if(product.getNumberOfViews() == null) {
			product.setNumberOfViews(Long.valueOf(0));
		}
		
		Product isAdded = productRepository.save(product);
			
		if (isAdded != null) {
			addProductInAnotherMicroService(product, "http://transaction-service/products/add");
		}
		
		return isAdded;
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product update(Product update, Long id) {
		
		Product current = getProductById(id);
		
		if(update!=null && current != null) {
			if(update.getPreOwned() != null) 
				current.setPreOwned(update.getPreOwned());
			if(update.getStatus() != null) 
				current.setStatus(update.getStatus());
			if(update.getArhived() != null) 
				current.setArhived(update.getArhived());
			if(update.getName() != null) 
				current.setName(update.getName());
			if(update.getLocation() != null) 
				current.setLocation(update.getLocation());
			if(update.getDescription() != null) 
				current.setDescription(update.getDescription());
			if(update.getPrice() != null) 
				current.setPrice(update.getPrice());
			if(update.getCategory() != null) 
				current.setCategory(update.getCategory());
			if(update.getSoldAt() != null) 
				current.setSoldAt(update.getSoldAt());
			if(update.getNumberOfViews() != null) 
				current.setNumberOfViews(update.getNumberOfViews());
		}
		
		return save(current);
	}

	@Override
	public void delete(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> search(String name) {
		if(!name.isEmpty())
			return productRepository.findByNameContaining(name);
		return new ArrayList<Product>();
	}

	@Override
	public List<Product> getActive(Long id) {
		return productRepository.findByUserIdAndArhived(id, Boolean.FALSE);
	}

	@Override
	public List<Product> getArhived(Long id) {
		return productRepository.findByUserIdAndArhived(id, Boolean.TRUE);
	}
	
	public void addProductInAnotherMicroService(Product product, String url) {
		URI uri = URIBuilder.fromUri(url).build();
    	RequestEntity<Product> request = RequestEntity.method(HttpMethod.POST, uri)
    											.contentType(MediaType.APPLICATION_JSON)
    											.body(product);
    	
		restTemplate.exchange(request, Boolean.class).getBody();
	}
	
	@Override
	public List<StatisticModel> getStatistic(Long time) {
		
		List<StatisticModel> list = new ArrayList<StatisticModel>();
		
		List<Category> categories = categoryManager.getAllCategory();
		
		categories.stream().forEach(category -> {
			list.add(new StatisticModel(category.getId(), category.getName(), 0));
		});
		
		getAllProducts().stream().forEach(product -> {
			if(product.getStatus() && product.getSoldAt() >= time){
				list.stream().forEach(s -> {
					if(s.getId() == product.getCategory().getId()){
						s.setValue(s.getValue() + 1);
					}
				});
			}
		});
		
		return list;
	}
	
	@Override
	public void sendMail(Long id, String body) {
		// Get session
		Session session = getSMTPSession();
		
		Product p = getProductById(id);

		if(p != null){
			try {
			    javax.mail.Message m = new MimeMessage(session);
			    m.setFrom(new InternetAddress("storagator@gmail.com"));
			    m.setRecipients(javax.mail.Message.RecipientType.TO,
				    InternetAddress.parse(p.getUser().getEmail()));
			    m.setSubject("Online Shop - " + p.getName());
			    m.setContent(body, "text/html");
			    // Send
			    Transport.send(m);
			} catch (MessagingException e) {
				System.out.print(e.getMessage());
			}
		}
		
	}
	
	 protected Session getSMTPSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", 587);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("storagator@gmail.com", "Storagator..11");
		    }
		});

		return session;
			
	 }
	
	public class StatisticModel{
		
		private Long id;
		private String name;
		private Integer value;
		
		public StatisticModel(Long id, String name, Integer value) {
			this.id = id;
			this.name = name;
			this.value = value;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}
}
