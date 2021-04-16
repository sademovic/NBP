package com.olx.user.service.businesslogic.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.olx.user.service.businesslogic.UserManager;
import com.olx.user.service.models.User;
import com.olx.user.service.repositories.UserRepository;

@Component
public class DefaultUserManager implements UserManager {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	


	@Override
	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}


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
	public void delete(Long id) {
		userRepository.deleteById(id);
	}


	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}


	@Override
	public User update(User update, Long id) {
		
		User current = getUserById(id);
		
		if(update!=null && current != null) {
			if(update.getPassword() != null) 
				current.setPassword(update.getPassword());
			if(update.getEmail() != null) 
				current.setEmail(update.getEmail());
			if(update.getFirstName() != null) 
				current.setFirstName(update.getFirstName());
			if(update.getLastName() != null) 
				current.setLastName(update.getLastName());
			if(update.getPhoneNumber() != null) 
				current.setPhoneNumber(update.getPhoneNumber());
			if(update.getLocation() != null) 
				current.setLocation(update.getLocation());
		}
		
		return save(current);
	}
}
