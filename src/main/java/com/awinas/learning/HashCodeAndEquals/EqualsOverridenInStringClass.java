package com.awinas.learning.HashCodeAndEquals;

// == will check for reference

// If two objects
// have the same hash code, it doesn't mean that they are equal. 
public class EqualsOverridenInStringClass {

	public static void main(String[] args) {

		String a = "awi";
		String b = "awi";
		String c = new String("awi");
		String d = c;
		System.out.println("hash code");
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		System.out.println(c.hashCode());
		System.out.println(d.hashCode());
		
		System.out.println(a == b);
		System.out.println(a == c);
		System.out.println(c == d);

		System.out.println(a.equals(b));
		System.out.println(a.equals(c));

	}

}
