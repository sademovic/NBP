package com.olx.message.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.olx.message.service.validation.Validation;
import com.olx.message.service.models.Chat;
import com.olx.message.service.models.User;

@Component
public class ChatModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Chat.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Chat chat = (Chat) target;
		
		if(chat == null) {
			
			errors.reject(Validation.NOT_SPECIFIED);
		
		} else  {
			
			User reciver = chat.getReceiver();
			User sender = chat.getSender();
			
			if (reciver == null) {
				errors.reject(Validation.RECEIVER_DOES_NOT_EXIST);
			}
			if (sender == null) {
				errors.reject(Validation.SENDER_DOES_NOT_EXIST);
			}		
		}	
	}
}
