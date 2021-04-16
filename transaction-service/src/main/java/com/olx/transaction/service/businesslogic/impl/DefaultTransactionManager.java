package com.olx.transaction.service.businesslogic.impl;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.olx.transaction.service.businesslogic.TransactionManager;
import com.olx.transaction.service.models.Product;
import com.olx.transaction.service.models.Transaction;
import com.olx.transaction.service.models.User;
import com.olx.transaction.service.repositories.TransactionRepository;

@Component
public class DefaultTransactionManager implements TransactionManager {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Transaction getTransactionById(Long id) {
		return transactionRepository.findById(id).orElse(null);
	}

	@Override
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction closeTransaction(Transaction transaction) {
		transaction.setClosed(true);
		try{
			Product arhived = closeTransactionInItemsMicroService(String.valueOf(transaction.getProduct().getId()));
			transaction.setProduct(arhived);
		}
		catch(Exception e){
			System.out.println("Product in not closed in itemsDB.");
		}
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> getClosedTransactions(User user) {
		return transactionRepository.findByOwnerAndClosed(user, Boolean.TRUE);
	}

	@Override
	public List<Transaction> getActiveTransactions(User user) {
		return transactionRepository.findByOwnerAndClosed(user, Boolean.FALSE);
	}

	@Override
	public void delete(Long id) {
		transactionRepository.deleteById(id);
	}
	
	public Product closeTransactionInItemsMicroService(String productId) {
		URI uri = URIBuilder.fromUri("http://itemservice/products/" + productId).build();
		Product body= new Product();
		body.setArhived(Boolean.TRUE);
    	RequestEntity<Product> request = RequestEntity.method(HttpMethod.PUT, uri)
    											.contentType(MediaType.APPLICATION_JSON)
    											.body(body);
    	
		return restTemplate.exchange(request, Product.class).getBody();
	}
}