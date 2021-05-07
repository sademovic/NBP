package com.olx.log.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.olx.log.service.businesslogic.LogManager;
import com.olx.log.service.models.Log;


@RestController
@RequestMapping("logs/")
public class LogController {
	
	@Autowired
	private LogManager logManager;

	@RequestMapping(method = RequestMethod.GET)
	public List<Log> gelAllLogs() {
		return logManager.getAllLogs();
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Log save(@RequestBody Log log ) {
		return logManager.save(log);
	}
}
