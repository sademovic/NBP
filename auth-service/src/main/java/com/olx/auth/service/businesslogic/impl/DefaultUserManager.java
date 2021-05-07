package com.olx.auth.service.businesslogic.impl;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.olx.auth.service.repositories.UserRepository;
import com.olx.auth.service.businesslogic.UserManager;
import com.olx.auth.service.models.User;

@Component
public class DefaultUserManager implements UserManager{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		List<User> result = new ArrayList<User>();
		for(User user: userRepository.findAll())
			result.add(user);
		return result;
	}
	
	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User add(User newUser) {
		return userRepository.save(newUser);
	}

	@Override
	public void delete(String email) {
		userRepository.delete(getUserByEmail(email));
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}	
}