package com.awinas.learning;

// casting char to int gives Ascii value of character
//For Ex :  ascii value of 'A' can be get by casting it to int
public class Ascii {

	public static void main(String[] args) {

		// Print All Ascii value
		/*
		 * for (int i = 0; i < 256; i++) { System.out.println((char) i); }
		 */

		System.out.println((int) 'A'); // 65
		System.out.println((int) 'a'); // 97

		// Print Ascii value of Capital letters
		for (int i = (int) 'a'; i <= (int) 'a' + 25; i++)
			System.out.println((char) i + " - " + i);

		for (int i = 'a'; i <= 'a' + 25; i++)
			System.out.println((char) i + " - " + i);

		// Print Ascii value of Small letters
		for (int i = (int) 'A'; i <= (int) 'A' + 25; i++)
			System.out.println((char) i + " - " + i);

	}

}
