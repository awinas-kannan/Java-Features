package com.awinas.learning.Java12;

/*
 * https://www.baeldung.com/java-12-new-features
 */
public class InstanceOf12 {

	public static void main(String[] args) {

		/*
		 * explicitly typecast < java 12
		 */
		
		Object obj = "Hello World!";
		if (obj instanceof String) {
			String s = (String) obj;
			System.out.println(s.length());
		}

		/*
		 * With Java 12, we can declare the new typecasted variable directly in the
		 * statement:
		 */

		if (obj instanceof String s) {
			System.out.println(s.length());
		}

	}

}
