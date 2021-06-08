package com.olx.user.service.businesslogic.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.olx.user.service.businesslogic.UserManager;
import com.olx.user.service.models.User;
import com.olx.user.service.repositories.UserRepository;

@Component
public class DefaultUserManager implements UserManager {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	


	@Override
	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		
		User isAdded = userRepository.save(user);
		
		if (isAdded != null) {
			addUserInAnotherMicroService(user, "http://items-service/users/add");
			addUserInAnotherMicroService(user, "http://message-service/users/add");
			addUserInAnotherMicroService(user, "http://transaction-service/users/add");
			addUserInAnotherMicroService(user, "http://auth-service/users/add");
		}
		
		return isAdded;
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
	
	@EventListener(ApplicationReadyEvent.class)
	private void setup() {
		User user = new User();
		user.setFirstName("Admin");
		user.setLastName("Admin");
		user.setEmail("ralilovic1@etf.unsa.ba");
		user.setPassword("admin");
		user.setRole("ADMIN");
		
		save(user);
	}
	
	
	private void addUserInAnotherMicroService(User user, String url) {
		URI uri = URIBuilder.fromUri(url).build();
    	RequestEntity<User> request = RequestEntity.method(HttpMethod.POST, uri)
    											.contentType(MediaType.APPLICATION_JSON)
    											.body(user);
    	
		restTemplate.exchange(request, Boolean.class);
	}
}
