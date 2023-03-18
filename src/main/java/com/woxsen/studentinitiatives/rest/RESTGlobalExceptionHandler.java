package com.woxsen.studentinitiatives.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.woxsen.studentinitiatives.entities.misc.ErrorMessage;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;
import com.woxsen.studentinitiatives.exceptions.InvalidTypeException;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RESTGlobalExceptionHandler {
	
	@ExceptionHandler(value = EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage entityNotFoundExceptionHandler(EntityNotFoundException e, WebRequest request) {
		ErrorMessage erMsg = new ErrorMessage(HttpStatus.NOT_FOUND,e.getMessage(), "false");
		return erMsg;
	}
	
	@ExceptionHandler(value = InvalidTypeException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ErrorMessage invalidTypeExceptionHandler(InvalidTypeException e, WebRequest request) {
		ErrorMessage erMsg = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage(), "false");
		return erMsg;
	}
	
	@ExceptionHandler(value = NoSuchFileFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage noSuchFileFoundExceptionHandler(NoSuchFileFoundException e, WebRequest request) {
		ErrorMessage erMsg = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage(),"false");
		return erMsg;
	}
	
	@ExceptionHandler(value = InvalidCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorMessage invalidCredentialsExceptionHandler(InvalidCredentialsException e, WebRequest request) {
		ErrorMessage erMsg = new ErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage(), "false");
		return erMsg;

	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage MethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
		ErrorMessage erMsg = new ErrorMessage(HttpStatus.BAD_REQUEST, "Wrong format of the provided parameter(s)","false");
		return erMsg;
	}
}
