package com.awinas.learning.coreconcepts.string;

public class Str {

	public static void main(String[] args) {
		String str1 = new String("Dell laptop");
		StringBuffer sbfr1 = new StringBuffer("Dell laptop");

		System.out.println(str1.equals(sbfr1)); // false
		System.out.println(sbfr1.equals(str1)); // false

		System.out.println(str1.contentEquals(sbfr1)); // true
		System.out.println(str1.getBytes()[6]);
	}

}
