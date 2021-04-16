package com.olx.items.service.businesslogic;

import com.olx.items.service.models.User;

public interface UserManager {
	
	User getUserById(Long id);
	
	User getUserByEmail(String email);
	
	void add(User user);
	
	void delete(String email);
}
