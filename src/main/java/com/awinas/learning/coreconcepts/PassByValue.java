package com.awinas.learning.coreconcepts;


/*
 * 
 * Modifying the Object (Affects Original)
 * Reassigning the Reference (Does NOT Affect Original)
 */
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


class Foo {
	private String attribute;

	public Foo(String a) {
		this.attribute = a;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
