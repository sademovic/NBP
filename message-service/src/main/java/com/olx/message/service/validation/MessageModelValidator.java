package com.olx.message.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.olx.message.service.validation.Validation;
import com.olx.message.service.models.Message;

@Component
public class MessageModelValidator implements Validator  {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Message.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Message message = (Message) target;
		
		if(message == null) {
			
			errors.reject(Validation.NOT_SPECIFIED);
		
		} else  {
				
			String body = message.getBody();
			if (!StringUtils.hasText(body)) {
			    errors.rejectValue("body", Validation.NOT_SPECIFIED);
			}
		}
	}
}