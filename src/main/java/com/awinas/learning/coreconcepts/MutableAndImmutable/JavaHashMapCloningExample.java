package com.awinas.learning.coreconcepts.MutableAndImmutable;

import java.util.HashMap;

public class JavaHashMapCloningExample {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		HashMap<Integer, MutableEmployee> employeeMap = new HashMap<>();

		employeeMap.put(1, new MutableEmployee("Awinas", "24", "Java"));
		employeeMap.put(2, new MutableEmployee("Viki", "24", "Py"));

		// Shallow clone
		HashMap<Integer, MutableEmployee> clonedMap = (HashMap<Integer, MutableEmployee>) employeeMap.clone();

		// Same as employeeMap
		System.out.println(clonedMap);

		System.out.println("\nChanges reflect in both maps \n");

		// Change a value is clonedMap
		clonedMap.get(1).setName("Clone breaker");

		// Verify content of both maps
		System.out.println(employeeMap);
		System.out.println(clonedMap);
		
		
		/////Mutable employee reference is same for both maps after cloning

	}
}