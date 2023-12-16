package com.awinas.learning.string;

//https://stackoverflow.com/questions/8798403/string-is-immutable-what-exactly-is-the-meaning#:~:text=String%20is%20immutable%20means%20that,the%20String%20literal%20%22ty%22%20.


//https://www.baeldung.com/java-string-immutable


//Why String is immutable

//3.2. Security
//If the String is MUTABLE then ,
//It could also happen that the String userName is visible to another thread, 
//which could then change its value after the integrity check.


//3.4. Hashcode Caching
/** Cache the hash code for the string */
//private int hash; // Default to 0  // check hashcode() method of string

//Thread safe
//String pool is possible only becux string is immutable
//Strings are thread safe... so i can be used in multiple threads
public class StringImmutable {

	public static void main(String[] args) {

		String a = "awinas";
		String b = "awinas";
		String c = "awinas";

		// all three variables are refering to same literal in string pool

		// String do not have a setter method to change its value
		//so its immutable 

		/** The value is used for character storage. */
		// String.class// private final char value[];
		
		//If the string has been mutable (c.setValue ("new val")) ,then changing the value of c 
		//would change the value of a , b as all 3 have same reference in pool
		
		

	}

}
