package com.olx.auth.service.businesslogic;

import java.util.List;

import com.olx.auth.service.models.User;

public interface UserManager {
	
	User getUserById(Long id);
	
	User getUserByEmail(String email);
	
	User add(User user);
	
	void delete(String email);

	List<User> getAllUsers();
}
