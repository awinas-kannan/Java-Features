package com.awinas.learning.coreconcepts.exceptionhandling;

//SQL exception
//sqle.getErrorCode()==RSA_TOKEN_NULL_OTP_ERROR_CODE)
public class ExceptionHandler2 {

	public static void main(String[] args) {

		try {
//			Validator.validate("a", null);
			String userName = "a";
			String pwd = null;
			if (userName == null || pwd == null) {
				throw new MyNullPtrException(userName == null ? "ENTER USER NAME " : "ENTER PWD");
			}
		} catch (MyNullPtrException e) {
			System.out.println("MyNullPtrException block");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("in exp block");
		}

	}

}
