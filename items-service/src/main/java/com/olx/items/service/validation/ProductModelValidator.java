package com.olx.items.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.olx.items.service.businesslogic.CategoryManager;
import com.olx.items.service.businesslogic.UserManager;
import com.olx.items.service.models.Category;
import com.olx.items.service.models.Product;
import com.olx.items.service.models.User;
import com.olx.items.service.validation.Validation;

@Component
public class ProductModelValidator implements Validator  {
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private CategoryManager categoryManager;

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Product product = (Product) target;
		
		if(product == null) {
			
			errors.reject(Validation.NOT_SPECIFIED);
		
		} else  {
				
			validatePreOwned(product, errors);
			validateStatus(product, errors);
			validateArhived(product, errors);
			validateName(product, errors);
			validateLocation(product, errors);
			validateDescription(product, errors);
			validatePrice(product, errors);
			validateUser(product, errors);
			validateCategory(product, errors);
	
		}
	}
	
	protected void validatePreOwned(Product product, Errors errors) {
		Boolean preOwned = product.getPreOwned();
		if (preOwned == null) {
		    errors.rejectValue("preOwned", Validation.NOT_SPECIFIED);
		}
	}
	
	protected void validateStatus(Product product, Errors errors) {
		Boolean status =product.getStatus();
		if (status == null) {
		    errors.rejectValue("status", Validation.NOT_SPECIFIED);
		}	
	}
	
	protected void validateArhived(Product product, Errors errors) {
		Boolean arhived = product.getArhived();
		if (arhived == null) {
		    errors.rejectValue("arhived", Validation.NOT_SPECIFIED);
		}
	}
	
	protected void validateName(Product product, Errors errors) {
		String name = product.getName();
		if (!StringUtils.hasText(name)) {
		    errors.rejectValue("name", Validation.NOT_SPECIFIED);
		}
	}
	
	protected void validateDescription(Product product, Errors errors) {
		String description = product.getDescription();
		if (!StringUtils.hasText(description)) {
		    errors.rejectValue("description", Validation.NOT_SPECIFIED);
		}
	}
	
	protected void validateLocation(Product product, Errors errors) {
		String location = product.getLocation();
		if (!StringUtils.hasText(location)) {
		    errors.rejectValue("location", Validation.NOT_SPECIFIED);
		}
	}
	
	protected void validatePrice(Product product, Errors errors) {
		Double price = product.getPrice();
		if (price == null) {
			errors.rejectValue("price", Validation.NOT_SPECIFIED);
		}
	}
	
	protected void validateUser(Product product, Errors errors) {
		User user =product.getUser();
		if (user == null) {
			errors.rejectValue("user", Validation.USER_DOES_NOT_EXIST);
		}
	}
	
	protected void validateCategory(Product product, Errors errors) {
		Category category = product.getCategory();
		if (category == null) {
			errors.rejectValue("category", Validation.CATEGORY_DOES_NOT_EXIST);
		}
	}
	
	public void validateUpdate(Product product, Errors errors) {
		if(product != null) {
			if(product.getUser() != null) {
				User user = userManager.getUserById(product.getUser().getId());
				if (user == null) {
					errors.rejectValue("user", Validation.USER_DOES_NOT_EXIST);
				}
			}
			
			if(product.getCategory() != null) {
				Category category = categoryManager.getCategoryById(product.getCategory().getId());
				if (category == null) {
					errors.rejectValue("category", Validation.CATEGORY_DOES_NOT_EXIST);
				}
			}
			if(product.getName() != null) {
				validateName(product, errors);
			}
			if(product.getLocation() != null) {
				validateLocation(product, errors);
			}
			if(product.getDescription() != null) {
				validateDescription(product, errors);
			}
			if(product.getPrice() != null) {
				validatePrice(product, errors);
			}
		}
	}
}
