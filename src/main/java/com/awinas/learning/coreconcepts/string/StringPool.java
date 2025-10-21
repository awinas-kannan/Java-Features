package com.awinas.learning.coreconcepts.string;

// == will check for reference

//  If two objects
// have the same hash code, it doesn't mean that they are equal. 

//https://www.javatpoint.com/string-pool-in-java

//https://www.baeldung.com/java-string-pool

//String pool is possible only because String is immutable in Java

//https://stackoverflow.com/questions/8798403/string-is-immutable-what-exactly-is-the-meaning#:~:text=String%20is%20immutable%20means%20that,the%20String%20literal%20%22ty%22%20.
public class StringPool {

	public static void main(String[] args) {

		String a = "awi";
		String b = "awi";
		String c = new String("awi");
		String d = c;
		String e = new String("awi").intern();
		
		System.out.println(a == b);
		System.out.println(a == c);
		System.out.println(c == d);
		//TRUE Because ,intern refers from string pool
		System.out.println(a == e);

		System.out.println(a.equals(b));
		System.out.println(a.equals(c));

	}

}
