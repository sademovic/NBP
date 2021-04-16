package com.olx.user.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.olx.user.service.businesslogic.UserManager;
import com.olx.user.service.models.Login;
import com.olx.user.service.models.User;

@Component
public class LoginModelValidator implements Validator {

	@Autowired
	private UserManager userManager;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Login.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Login login = (Login) target;
		
		if(login == null) {
			errors.reject(Validation.NOT_SPECIFIED);
		
		} else  {
			
			String email = login.getEmail();
			String password = login.getPassword();
			
			if (!StringUtils.hasText(email)) {
			    errors.rejectValue("email", Validation.NOT_SPECIFIED);
			}
			if (!StringUtils.hasText(password)) {
				errors.rejectValue("password", Validation.NOT_SPECIFIED);
			} 
			
			if(!errors.hasErrors())  {
				User exist = userManager.getUserByEmail(email);
				if(exist == null) {
					 errors.rejectValue("email", Validation.USER_DOES_NOT_EXISTS);
				} else {
					if(!password.equals(exist.getPassword())) {
						errors.rejectValue("password", Validation.USER_PASSWORD_INCORRECT);
					}
				}
			}	
		}	
	}
}
