package com.olx.transaction.service.businesslogic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olx.transaction.service.businesslogic.ProductManager;
import com.olx.transaction.service.models.Product;
import com.olx.transaction.service.repositories.ProductRepository;

@Component
public class DefaultProductManager implements ProductManager {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public void add(Product newProduct) {
		productRepository.save(newProduct);
	}

	@Override
	public void delete(Long id) {
		productRepository.deleteById(id);
	}
}