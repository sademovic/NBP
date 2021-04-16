package com.olx.items.service.businesslogic.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.olx.items.service.businesslogic.ProductManager;
import com.olx.items.service.models.Product;
import com.olx.items.service.repositories.ProductRepository;

@Component
public class DefaultProductManager implements ProductManager {

	@Autowired
	private ProductRepository productRepository;
	
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
}
