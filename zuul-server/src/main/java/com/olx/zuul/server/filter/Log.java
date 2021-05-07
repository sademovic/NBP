package com.olx.zuul.server.filter;

public class Log {
	
	private Long timestamp;
	
	private String username;
	
	private String microservice;
	
	private String methodType; 
	
	private String resurs;
	
	private Integer statusCode;
	
	private String bodyResponse;
	
	
	public Log(Long timestamp, String username, String microservice, String methodType, String resurs,
			Integer statusCode, String bodyResponse) {
		this.timestamp = timestamp;
		this.username = username;
		this.microservice = microservice;
		this.methodType = methodType;
		this.resurs = resurs;
		this.statusCode = statusCode;
		this.bodyResponse = bodyResponse;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMicroservice() {
		return microservice;
	}
	public void setMicroservice(String microservice) {
		this.microservice = microservice;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getResurs() {
		return resurs;
	}
	public void setResurs(String resurs) {
		this.resurs = resurs;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getBodyResponse() {
		return bodyResponse;
	}
	public void setBodyResponse(String bodyResponse) {
		this.bodyResponse = bodyResponse;
	}
	
	@Override
	public String toString() {
		return "Log [timestamp=" + timestamp + ", username=" + username + ", microservice=" + microservice
				+ ", methodType=" + methodType + ", resurs=" + resurs + ", statusCode=" + statusCode + ", bodyResponse="
				+ bodyResponse + "]";
	}
	
}
