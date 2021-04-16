package com.olx.user.service.validation;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.olx.user.service.businesslogic.UserManager;
import com.olx.user.service.models.User;


@Component
public class UserModelValidator implements UserValidator {
	
	@Autowired
	private UserManager userManager;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		User user = (User) target;
		
		if (user == null) {
		    errors.reject(Validation.NOT_SPECIFIED);
		} else {
			validatePassword(user, errors);
			validateEmail(user, errors);
			validateFirstName(user, errors);
			validateLastName(user, errors);
			validatePhoneNumber(user, errors);
			validateLocation(user, errors);
		}
	}

	@Override
	public void validatePassword(User user, Errors errors) {
		
		String password = user.getPassword();
		
		if (!StringUtils.hasText(password)) {
		    errors.rejectValue("password", Validation.NOT_SPECIFIED);
		} else {
			
			if(password.length() < 6){
			    errors.rejectValue("password", Validation.PASSWORD_LENGTH);
			} 
			
			/*if(!CharUtil.containsUpperCase(password)){
			    errors.rejectValue("password", Validation.PASSWORD_UPPER_CASE);
			} 
			
			if(!CharUtil.containsLowerCase(password)){
			    errors.rejectValue("password", Validation.PASSWORD_LOWER_CASE);
			} 
			
			if(!CharUtil.containsNumber(password)){
			    errors.rejectValue("password", Validation.PASSWORD_NUMBER);
			} 
			
			if (!CharUtil.containsSpecial(password)) {
			    errors.rejectValue("password", Validation.PASSWORD_SPECIAL);
			}*/
		}

	}

	@Override
	public void validateEmail(User user, Errors errors) {
		
		String email = user.getEmail();
		
		if (!StringUtils.hasText(email)) {
			errors.rejectValue("email", Validation.NOT_SPECIFIED);
		
		} else {
			
			User alreadyExists = userManager.getUserByEmail(email);
			if (alreadyExists != null && !alreadyExists.getId().equals(user.getId())) {
				errors.rejectValue("email", Validation.ALREADY_EXISTS);
			}
			
			Pattern p = Pattern.compile(Validation.REGEX_EMAIL); 
			if(!validateRegex(p,email)){
				//errors.rejectValue("email", Validation.EMAIL_INVALID);
			}
			
		}
	}

	@Override
	public void validateFirstName(User user, Errors errors) {
		Pattern p = Pattern.compile(Validation.REGEX_TEXT); 
		String firstName = user.getFirstName();
		
		if (!StringUtils.hasText(firstName)) {
			errors.rejectValue("firstName", Validation.NOT_SPECIFIED);
		
		} else if(!validateRegex(p,firstName)) {
			errors.rejectValue("firstName", Validation.FIRSTNAME_INVALID);
		}
	}

	@Override
	public void validateLastName(User user, Errors errors) {
		Pattern p = Pattern.compile(Validation.REGEX_TEXT); 
		String lastName = user.getLastName();
		
		if (!StringUtils.hasText(lastName)) {
			errors.rejectValue("lastName", Validation.NOT_SPECIFIED);
		
		}else if(!validateRegex(p,lastName)) {
			errors.rejectValue("lastName", Validation.LASTNAME_INVALID);
		}
	}	

	@Override
	public void validatePhoneNumber(User user, Errors errors) {
		
		Pattern p = Pattern.compile(Validation.REGEX_PHONENUMBER); 
		String number = user.getPhoneNumber();
		
		if (!StringUtils.hasText(number)) {
			errors.rejectValue("phoneNumber", Validation.NOT_SPECIFIED);
		}
		else if(!validateRegex(p,number)) {
			errors.rejectValue("phoneNumber", Validation.PHONE_NUMBER_INVALID);
		}	
	}

	@Override
	public void validateLocation(User user, Errors errors) {
		
		Pattern p = Pattern.compile(Validation.REGEX_TEXT); 
		String location = user.getLocation();
		
		if (!StringUtils.hasText(location)) {
			errors.rejectValue("location", Validation.NOT_SPECIFIED);
		
		}else if(!validateRegex(p,location)) {
			errors.rejectValue("location", Validation.LOCATION_INVALID);
		}
	}
	
	protected boolean validateRegex(Pattern p, String s) {
		Matcher m = p.matcher(s); 
		return m.find() && m.group().equals(s);
	}
	
	protected void validateEmail2(User user, Errors errors) {
		String email = user.getEmail();
		Pattern p = Pattern.compile(Validation.REGEX_EMAIL); 
		if(!validateRegex(p,email)){
			errors.rejectValue("email", Validation.EMAIL_INVALID);
		}
	}

	@Override
	public void validateUpdate(User user, Errors errors) {
		if(user!=null) {
			if(user.getPassword() != null) 
				validatePassword(user, errors);
			if(user.getEmail() != null) 
				validateEmail2(user, errors);
			if(user.getFirstName() != null) 
				validateFirstName(user, errors);
			if(user.getLastName() != null) 
				validateLastName(user, errors);
			if(user.getPhoneNumber() != null) 
				validatePhoneNumber(user, errors);
			if(user.getLocation() != null) 
				validateLocation(user, errors);
		}	
	}
}
