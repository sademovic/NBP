package com.olx.log.service.businesslogic.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olx.log.service.businesslogic.LogManager;
import com.olx.log.service.models.Log;
import com.olx.log.service.repositories.LogRepository;

@Component
public class DefaultLogManager implements LogManager {
	
	@Autowired
	LogRepository logRepository;

	@Override
	public List<Log> getAllLogs() {
		List<Log> result = new ArrayList<Log>();
		for(Log user: logRepository.findAll())
			result.add(user);
		return result;
	}

	@Override
	public Log save(Log log) {
		if(log != null && !log.getUsername().equalsIgnoreCase("guest") && !log.getUsername().contains("@")) {
			List<Log> list = logRepository.findByBodyResponseContaining(log.getUsername());
			String email = !list.isEmpty() ? list.get(0).getUsername() : null;
			if(email != null) {
				log.setUsername(email);
			}
		}
		if(log.getUsername().length()>50) log.setUsername("guest");
		
		if (log.getBodyResponse().length() > 2000) 
		{
		    log.setBodyResponse(log.getBodyResponse().substring(0, 2000));
		} 

		return logRepository.save(log);
	}
}