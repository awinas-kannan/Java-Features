package com.awinas.learning.coreconcepts.exceptionhandling;

public class InvalidCredsException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidCredsException(String msg) {
		super(msg);
	}

	public InvalidCredsException(Exception e) {
		super(e);
	}

	public InvalidCredsException(String msg, Exception e) {
		super(msg, e);
	}
}
