package com.awinas.learning.coreconcepts.MutableAndImmutable;

import java.util.Date;

class TestMain {
	public static void main(String[] args) {
		Date date = new Date();
		ImmutableClass im = ImmutableClass.createNewInstance(100, "test", date);
		System.out.println(im);

		System.out.println("After trying to modify - Same value  // Deep copy of date");
		tryModification(im.getImmutableField1(), im.getImmutableField2(), im.getMutableField());
		System.out.println(im);

		System.out.println("After trying to modify - Diff value for value  // Shallow copy of date");
		// Date is mutable as it has Setter to modify its value
		// im.getShallowMutableField()
		// date.setDate(10);
		tryModification(im.getImmutableField1(), im.getImmutableField2(), im.getShallowMutableField());
		System.out.println(im);
	}

	private static void tryModification(Integer immutableField1, String immutableField2, Date mutableField) {
		immutableField1 = 10000;
		immutableField2 = "test changed";
		mutableField.setDate(10);
	}
}