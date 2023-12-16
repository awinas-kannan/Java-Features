package com.awinas.learning;

public class PassByValue {

	public static void main(String[] args) {
		Foo f = new Foo("Actual Value");
		changeReference(f); // It won't change the reference formal param f !
		System.out.println(f.getAttribute());
		modifyReference(f); // It will change the object that the reference variable "f" refers to!
		System.out.println(f.getAttribute());
	}

	public static void changeReference(Foo a) {
		Foo b = new Foo("new Value");
		a = b; // If it had been passby reference , then assiging a=b would have changed the  value of f
	}

	public static void modifyReference(Foo c) {
		c.setAttribute("Changing actual value");
	}

}
