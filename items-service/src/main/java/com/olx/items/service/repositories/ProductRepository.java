package com.olx.items.service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olx.items.service.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	
	List<Product> findByUserId(Long id);
	
	List<Product> findByNameContaining(String name);
	
	List<Product> findByUserIdAndArhived(Long id, Boolean condition);

	@Transactional 
	void deleteById(Long id);
}
