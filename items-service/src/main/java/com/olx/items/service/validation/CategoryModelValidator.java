package com.olx.items.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.olx.items.service.models.Category;

@Component
public class CategoryModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Category.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Category category = (Category) target;
		
		if(category == null) {
			
			errors.reject(Validation.NOT_SPECIFIED);
		
		} else  {
			
			String name = category.getName();
			if (!StringUtils.hasText(name)) {
			    errors.rejectValue("name", Validation.NOT_SPECIFIED);
			}
		}	
	}
}
