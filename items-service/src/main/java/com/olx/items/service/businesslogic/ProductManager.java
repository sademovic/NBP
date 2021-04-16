package com.olx.items.service.businesslogic;

import java.util.List;

import com.olx.items.service.models.Product;

public interface ProductManager {
	
	List<Product> getAllProducts();
	
	Product save(Product product);
	
	Product getProductById(Long id);
	
	Product update(Product product, Long id);

	List<Product> search(String name);
	
	List<Product> getActive(Long id);
	
	List<Product> getArhived(Long id);
	
	void delete(Long id);

}
