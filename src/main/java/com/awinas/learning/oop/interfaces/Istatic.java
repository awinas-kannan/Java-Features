package com.awinas.learning.oop.interfaces;

public interface Istatic {

	void normalmethod();
	
	static void staticMethod1() {
		System.out.println("static method 1");
	}
	
	static void staticMethod2() {
		System.out.println("static method 2");
	}
	
}
