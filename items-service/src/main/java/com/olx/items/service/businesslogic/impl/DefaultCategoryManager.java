package com.olx.items.service.businesslogic.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olx.items.service.businesslogic.CategoryManager;
import com.olx.items.service.models.Category;
import com.olx.items.service.repositories.CategoryRepository;

@Component
public class DefaultCategoryManager implements CategoryManager {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategory() {
		List<Category> result = new ArrayList<Category>();
		for(Category c: categoryRepository.findAll())
			result.add(c);
		return result;
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public Category update(Category updated, Long id) {

		Category current = getCategoryById(id);
		
		if(current != null && updated != null)
			if(updated.getName() != null)
				current.setName(updated.getName());
			
		return save(current);
	}

	@Override
	public void delete(Long id) {
		categoryRepository.deleteById(id);
	}

}
