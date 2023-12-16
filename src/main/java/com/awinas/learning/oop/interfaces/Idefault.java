package com.awinas.learning.oop.interfaces;

public interface Idefault {

	void normalMethod();

	default void defMethod() {
		System.out.println("Default method in interface");
	}

    default void defMethod1() {
		System.out.println("Default method  1 in interface");
	}
}
