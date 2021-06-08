package com.olx.message.service.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.message.service.businesslogic.ChatManager;
import com.olx.message.service.businesslogic.UserManager;
import com.olx.message.service.models.Chat;
import com.olx.message.service.models.Message;
import com.olx.message.service.models.User;
import com.olx.message.service.validation.ChatModelValidator;
import com.olx.message.service.validation.MessageModelValidator;
import com.olx.message.service.validation.Validation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("chat/")
@Api(tags = { "Chat Controller" })
public class ChatController {
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ChatManager chatManager;
	
	@Autowired
	private ChatModelValidator chatModelValidator;
	
	@Autowired
	private MessageModelValidator messageModelValidator;
	
	@ApiOperation(value = "Get my chat", notes = "This service method is used to get all chat from specific user.")
	@RequestMapping(value = "my", method = RequestMethod.POST)
    public ResponseEntity<Object>  getMyChat(@RequestBody RequestForm form,
    										 Errors errors) {
	
		User user = userManager.getUserById(form.userId);
		if(user == null) {
			errors.reject(Validation.CHAT_DOES_NOT_EXIST);
			return new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(chatManager.getMyChat(user), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Add message to caht", notes = "This service method is used to add a message to the specific chat.")
	@RequestMapping(value = "{id}/message/add", method = RequestMethod.POST)
    public ResponseEntity<Object>  addMessage(@RequestBody Message message, 
    						  @PathVariable(required = true) Long id,
    						  Errors errors) {
		
		messageModelValidator.validate(message, errors);
		Chat chat = chatManager.getChatById(id);
		
		if(chat == null || errors.hasErrors()) {		
			if(chat == null)
				errors.reject(Validation.CHAT_DOES_NOT_EXIST);
			return new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		Date date = new Date();
		message.setCratedAt(date.getTime());
		chat.addMessage(message);
		return new ResponseEntity<Object>(chatManager.save(chat), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Add new chat", notes = "This service method is used to add a chat.")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object>  addChat(@RequestBody Message message, 
    									   @RequestParam(required = true) Long receiverId,
    									   @RequestParam(required = true) Long senderId,
    						               Errors errors) {

		User receiver = userManager.getUserById(receiverId);
		User sender = userManager.getUserById(senderId);
		
		Chat chat = new Chat();
		chat.setSender(sender);
		chat.setReceiver(receiver);
		
		chatModelValidator.validate(chat, errors);		
		
		if(errors.hasErrors()) {		
			return new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>(chatManager.save(chat), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Delete chat", notes = "This service method is used to delete chat by id.")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteChatById(@PathVariable("id") Long id) {
		Chat chat = chatManager.getChatById(id);
		if(chat == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji chat sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		chatManager.delete(id);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
    }	
	
	public static class RequestForm {
		public Long userId;
	}
}