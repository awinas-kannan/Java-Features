package com.awinas.learning.coreconcepts.exceptionhandling;

//SQL exception
//sqle.getErrorCode()==RSA_TOKEN_NULL_OTP_ERROR_CODE)
public class ExceptionHandler {

	public static void main(String[] args) {

		try {
			Validator.validate("a", null);
		} catch (MyNullPtrException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (UserNameInvalidException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (InvalidCredsException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		try {
			Validator.check();
		} catch (RuntimeException re) {
			System.out.println(re.getClass().getName());
		}
	}

}
