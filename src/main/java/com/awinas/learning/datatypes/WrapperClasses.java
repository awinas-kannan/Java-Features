package com.awinas.learning.datatypes;

public class WrapperClasses {

	public static void main(String[] args) {

		// 1. using constructor
		Integer intObj = new Integer(10);
		Integer xx = new Integer("10");

		// 2. using static factory method
		Integer intAnoterObj = Integer.valueOf(10);

		String intVal = "1000";
		Integer parsedInt = Integer.parseInt(intVal);
		System.out.println(parsedInt);

		// BOXING and UNBOXING
		Integer boxing = 10; // primitive to wrapper

		int unbox = new Integer(10); // Wrapper to primitive

		System.out.println(boxing + unbox);

		//
		Long x = new Long(12);
		Long.valueOf(12);

	}

}
