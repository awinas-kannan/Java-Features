package com.awinas.learning.exceptionhandling;

public class UserNameInvalidException extends Exception{
	private static final long serialVersionUID = 1L;

	public UserNameInvalidException(String msg) {
		super(msg);
	}

	public UserNameInvalidException(Exception e) {
		super(e);
	}

	public UserNameInvalidException(String msg, Exception e) {
		super(msg, e);
	}
}
