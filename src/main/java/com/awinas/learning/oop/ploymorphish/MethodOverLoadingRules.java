package com.awinas.learning.oop.ploymorphish;

public class MethodOverLoadingRules {

	public static void main(String[] args) {

	}

	// 1 : change method signature.

	// Overloaded method
	public Integer sum(Integer a, Integer b) {
		return a + b;
	}

	// Overloading method
	public Integer sum(Float a, Integer b) { // Valid
		return null;
	}

	// 2:Return type of method is never part of method signature, so only changing
	// the return type of method does not amount to method overloading

	// Overloaded method
	public Integer sum_1(Integer a, Integer b) {
		return a + b;
	}

	// Overloading method
//	public Float sum_1(Integer a, Integer b) { // Not valid; Compile time error
//		return null;
//	}

	// 3:Thrown exceptions from methods are also not considered when overloading a
	// method.

	// Overloaded method
	public Integer sum_2(Integer a, Integer b) throws NullPointerException {
		return a + b;
	}

	// Overloading method
//    public Integer sum_2(Integer a, Integer b) throws Exception{  //Not valid; Compile time error
//        return null;
//    }
	
	

}
