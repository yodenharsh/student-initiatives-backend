package com.woxsen.studentinitiatives.entities.misc;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ErrorMessage {
	
	private HttpStatusCode httpCode;
	private String message;
	public ErrorMessage(HttpStatus httpCode, String message) {
		this.httpCode = httpCode;
		this.message = message;
	}
	public HttpStatusCode getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(HttpStatusCode httpCode) {
		this.httpCode = httpCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
