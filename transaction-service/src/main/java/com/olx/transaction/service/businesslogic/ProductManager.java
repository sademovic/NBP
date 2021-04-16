package com.olx.transaction.service.businesslogic;

import com.olx.transaction.service.models.Product;

public interface ProductManager {
	
	Product getProductById(Long id);
	
	void add(Product newProduct);
	
	void delete(Long id);
}
