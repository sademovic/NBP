package com.olx.transaction.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.olx.transaction.service.validation.Validation;
import com.olx.transaction.service.models.Product;
import com.olx.transaction.service.models.Transaction;
import com.olx.transaction.service.models.User;

@Component
public class TransactionModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Transaction.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Transaction transaction = (Transaction) target;
		
		if(transaction == null) {
			
			errors.reject(Validation.NOT_SPECIFIED);
		
		} else  {
			
			User owner = transaction.getOwner();
			User buyer = transaction.getBuyer();
			Product product = transaction.getProduct();
			
			if (owner == null) {
				errors.reject(Validation.USER_DOES_NOT_EXIST);
			}
			if (buyer == null) {
				errors.reject(Validation.USER_DOES_NOT_EXIST);
			}
			if (product == null) {
				errors.reject(Validation.PRODUCT_DOES_NOT_EXIST);
			}	
		}	
	}
}