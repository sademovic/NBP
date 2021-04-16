package com.olx.items.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.olx.items.service.businesslogic.UserManager;
import com.olx.items.service.models.User;

@RestController
@RequestMapping("users/")
public class UserController {
	
	@Autowired
	private UserManager userManager;

	@RequestMapping(value = "add", method = RequestMethod.POST)
    public void addUser(@RequestBody User user) {
		userManager.add(user);
    }
	
	@RequestMapping(value = "{email}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("email") String email) {
		userManager.delete(email);
    }
}