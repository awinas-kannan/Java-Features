package com.awinas.learning.exceptionhandling;

public class Validator {

	private static final String USER = "awi";
	private static final String PWD = "2719";

	public static boolean validate(String userName, String pwd)
			throws MyNullPtrException, UserNameInvalidException, InvalidCredsException {

		if (userName == null || pwd == null) {
			throw new MyNullPtrException(userName == null ? "ENTER USER NAME " : "ENTER PWD");
		} else if (!USER.equals(userName)) {
			throw new UserNameInvalidException("User name is invalid");
		} else if (!USER.equals(userName) || !PWD.equals(pwd)) {
			throw new InvalidCredsException("Enter valid credentials");
		}
		return true;

	}

	public static void check() {
		System.out.println(0/18);
		System.out.println(18/0);
	}
}
