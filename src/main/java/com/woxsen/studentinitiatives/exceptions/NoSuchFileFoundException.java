package com.woxsen.studentinitiatives.exceptions;

public class NoSuchFileFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public NoSuchFileFoundException(String msg) {
		super(msg);
	}
}
