package com.awinas.learning.Java09;

import java.util.Arrays;
import java.util.List;

public class ListofvsArraysAsList {

	public static void main(String[] args) {

		/*
		 * 1. Mutability – Arrays.asList is mutable which means that you cannot add or
		 * remove elements from the list. However, you can modify the existing elements
		 * in the list.
		 *
		 */

		String[] array = { "apple", "banana", "cherry",null };
		List<String> list = Arrays.asList(array);
		list.set(0, "orange"); // Changes "apple" to "orange"
		System.out.println(list);

//		list.add("Grape"); Exception in thread "main" java.lang.UnsupportedOperationException

		/*
		 * 
		 * List.of is immutable, meaning you cannot add, remove or modify elements in
		 * the list. If you try to modify the list, you will get an
		 * UnsupportedOperationException Example – Creates a new instance of the List
		 * interface.
		 */

		List<String> listOf = List.of("apple", "banana", "cherry");
//		listOf = List.of(array);
		listOf.set(0, "orange"); // Throws UnsupportedOperationException
		
		
	}
}
