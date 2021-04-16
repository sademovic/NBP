package com.olx.items.service.businesslogic;

import java.util.List;

import com.olx.items.service.models.Category;

public interface CategoryManager {
	
	List<Category> getAllCategory();
	
	Category save(Category category);
	
	Category getCategoryById(Long id);
	
	Category update(Category product, Long id);
	
	void delete(Long id);

}
