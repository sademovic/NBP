package com.olx.log.service.controller;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.olx.log.service.businesslogic.LogManager;
import com.olx.log.service.models.Log;


@RestController
@RequestMapping("logs")
@Api(tags = { "Log Controller" })
public class LogController {
	
	@Autowired
	private LogManager logManager;

	@ApiOperation(value = "Get logs", notes = "This service method is used to get all logs.")
	@RequestMapping(method = RequestMethod.GET)
	public List<Log> gelAllLogs() {
		return logManager.getAllLogs();
	}
	
	@ApiOperation(value = "Save log", notes = "This service method is used to save log.")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Log save(@RequestBody Log log) {
		return logManager.save(log);
	}
	
	@ApiOperation(value = "Get log by id", notes = "This service method is used to get log by id.")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		logManager.delete(id);
	}
}
