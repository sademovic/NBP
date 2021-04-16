package com.olx.items.service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olx.items.service.models.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

	@Transactional 
	void deleteById(Long id);
}
