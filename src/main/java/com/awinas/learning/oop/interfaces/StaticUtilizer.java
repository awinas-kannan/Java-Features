package com.awinas.learning.oop.interfaces;

public class StaticUtilizer implements Istatic {

	@Override
	public void normalmethod() {
		System.out.println("Normal method");

	}

	static void staticMethod1() {
		System.out.println("static method with same name in child");
	}
}
