package com.olx.log.service.businesslogic;

import java.util.List;
import com.olx.log.service.models.Log;

public interface LogManager {
	
	List<Log> getAllLogs();
	
	Log save(Log log);
}