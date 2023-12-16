package com.awinas.learning.Java10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReturnImmutableListStream {

	/*
	 * It doesn’t allow the null values. It throws NullPointerException if it is
	 * presented with a null value.
	 */
	public static void main(String[] args) {
		// Creating an object of mutable list
		var mutableListOfNumber = new ArrayList<Integer>();
		mutableListOfNumber.add(1);
		mutableListOfNumber.add(2);
		System.out.println("Mutable list: " + mutableListOfNumber);
		List<Integer> immutableList = mutableListOfNumber.stream().collect(Collectors.toUnmodifiableList());
		System.out.println("Immutable list: " + immutableList);
//		immutableList.add(5); //java.lang.UnsupportedOperationException
		
		
		// Creating an object of mutable HashSet
		Set<Integer> mutableSetOfNumber = new HashSet<Integer>();
		mutableSetOfNumber.add(1);
		mutableSetOfNumber.add(2);
		mutableSetOfNumber.add(1);
		System.out.println("Mutable Set: " + mutableSetOfNumber);
		Set<Integer> immutableSetOfNumber = mutableSetOfNumber.stream().collect(Collectors.toUnmodifiableSet());
		System.out.println("Immutable list: " + immutableSetOfNumber);

		
		// https://javaconceptoftheday.com/java-10-collectors-methods/
		Map<String, Integer> unModifiableMap = Stream
				.of("Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
				.collect(Collectors.toUnmodifiableMap(s -> s, String::length));
		System.out.println(unModifiableMap);

		Map<Integer, String> unModifiableMap1 = Stream
				.of("Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
				.collect(Collectors.toUnmodifiableMap(String::length, s -> s, (s1, s2) -> s1 + ", " + s2));
		System.out.println(unModifiableMap1);

	}

}
