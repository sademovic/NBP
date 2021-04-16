package com.olx.transaction.service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.olx.transaction.service.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	
}
