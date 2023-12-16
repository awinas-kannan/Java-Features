package com.awinas.learning.oop.ploymorphish;

public class ParentClass {

	String getNumber(Number n) {
		return "Parent class number";

	}

//	String getNumber(Integer n) {
//		return "Parent class number";
//
//	}

	String getNumber(Number n, Number n2) {
		return "Parent class number - over loaded method getNumber";

	}
}
