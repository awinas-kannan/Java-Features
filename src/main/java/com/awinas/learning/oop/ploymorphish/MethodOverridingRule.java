package com.awinas.learning.oop.ploymorphish;

import java.io.FileNotFoundException;

public class MethodOverridingRule {

	// Overriden method
	public Number sum(Integer a, Integer b) {
		privateMethod();
		return a + b;
	}
	
	public Integer sum(Integer a) {
		return a;
	}

	private void privateMethod() {

	}

	public final String finalMethod() {
		return "final method";

	}

	// Overriden method
	public Integer checkedException(Integer a, Integer b) throws FileNotFoundException {
		return a + b;
	}

	public Integer reducingscope(Integer a, Integer b) {
		return a + b;
	}
	
	protected Integer increasingscope(Integer a, Integer b) {
		return a + b;
	}
}
