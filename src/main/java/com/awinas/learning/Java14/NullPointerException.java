package com.awinas.learning.Java14;

public class NullPointerException {

	public static void main(String[] args) {
		/*
		 * Exception in thread "main" java.lang.NullPointerException: Cannot invoke
		 * "String.getBytes()" because the return value of
		 * "com.awinas.learning.Java14.NullPointerException.getNull()" is null at
		 * com.awinas.learning.Java14.NullPointerException.main(NullPointerException.
		 * java:6)
		 * 
		 */
		new NullPointerException().getNull().getBytes();
	}

	String getNull() {
		String z = null;
		return z;
	}
}
