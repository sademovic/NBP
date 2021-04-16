package com.olx.message.service.businesslogic;

import com.olx.message.service.models.User;

public interface UserManager {
	
	User getUserById(Long id);
	
	User getUserByEmail(String email);
	
	void add(User user);
	
	void delete(String email);
}