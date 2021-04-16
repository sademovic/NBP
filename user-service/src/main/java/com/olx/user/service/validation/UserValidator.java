package com.olx.user.service.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.olx.user.service.models.User;

public interface UserValidator extends Validator {
	
    void validatePassword(User user, Errors errors);

    void validateEmail(User user, Errors errors);
    
    void validateFirstName(User user, Errors errors);
    
    void validateLastName(User user, Errors errors);
    
    void validatePhoneNumber(User user, Errors errors);
    
    void validateLocation(User user, Errors errors);
    
    void validateUpdate(User user, Errors errors);

}
