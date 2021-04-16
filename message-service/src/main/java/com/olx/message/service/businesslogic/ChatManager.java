package com.olx.message.service.businesslogic;

import java.util.List;

import com.olx.message.service.models.Chat;
import com.olx.message.service.models.User;

public interface ChatManager {
	
	List<Chat> getMyChat(User user);
	
	Chat save(Chat chat);
	
	Chat getChatById(Long id);
	
	void delete(Long id);
}