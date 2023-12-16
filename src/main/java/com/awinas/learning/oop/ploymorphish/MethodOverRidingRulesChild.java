package com.awinas.learning.oop.ploymorphish;

import java.io.FileNotFoundException;
import java.io.IOException;

//https://howtodoinjava.com/java/oops/method-overloading-overriding/
public class MethodOverRidingRulesChild extends MethodOverridingRule {

//	1) The method argument list in overridden and overriding methods must be exactly same 
//	If they don’t match, you will end up with an overloaded method.
//
//	2) The return type of overriding method can be child class of return type declared 
//	   in overridden method.

	// overriding method
	//From NUmber to Integer
	@Override
	public Integer sum(Integer a, Integer b) { // Integer extends Number; so it's valid
		return a + b;
	}

	//Integer to Number -- Not possible -- Child to parent
//	@Override
//	public Number sum(Integer a) {
//		return a;
//	}
	
	// 3) Above all rules, private, static and final methods can not be overridden
	// in java in any way. As simple as that !!

//	@Override
//	private void privateMethod() {
//
//	}

//	@Override
//	public final String finalMethod() {
//		return "final method";
//
//	}

	// 4)Overriding method cannot throw checked Exception higher in hierarchy than
	// thrown by overridden method
	// Eventhouhh IO exception is parent of Filenotfound exception it is not
	// permitted to
	// throw super class exception in child class overriding method

	// not valid
//	@Override
//	public Integer checkedException(Integer a, Integer b) throws IOException {
//		return a + b;
//	}

// It's valid; Don't declare the exception at all is permitted.
	public Integer checkedException(Integer a, Integer b) {
		return a + b;
	}

	// 5 :Also note that overriding method can not reduce the access scope of
	// overridden method
	// If i have public method in superclass, and when you over ride in child class
	// the scope should not be redudec (I.e should not make it protected
	// ,private...)

	// Err -Cannot reduce the visibility of the inherited method from
	// MethodOverridingRule
//	@Override
//	protected Integer reducingscope(Integer a, Integer b) {
//		return a + b;
//	}

	@Override
	public Integer increasingscope(Integer a, Integer b) {
		return a + b;
	}

}
