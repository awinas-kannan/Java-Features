package com.awinas.learning.coreconcepts.exceptionhandling;

public class MyNullPtrException  extends Exception {

	private static final long serialVersionUID = 1L;

	public MyNullPtrException(String msg) {
		super(msg);
	}

	public MyNullPtrException(Exception e) {
		super(e);
	}

	public MyNullPtrException(String msg, Exception e) {
		super(msg, e);
	}

}
