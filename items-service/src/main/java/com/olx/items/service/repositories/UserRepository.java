package com.olx.items.service.repositories;

import org.springframework.data.repository.CrudRepository;

import com.olx.items.service.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
}
