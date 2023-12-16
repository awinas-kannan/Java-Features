package com.awinas.learning.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Array List features

//Ordered 

//Index based

//Dynamic resixing

//non syncronised

//duplicated allowed

public class MyArrayList {

	public static void main(String[] args) {

		System.out.println("*******Declaration ***********");
		List<Integer> numbers = new ArrayList<>();

		// Generic Arraylist with the given capacity
		List<Integer> numbers1 = new ArrayList<>(3);
		numbers1.add(3);
		numbers1.add(3);
		numbers1.add(4);
		numbers1.add(4);
		numbers1.add(3);

		// Generic Arraylist initialized with another collection
		List<Integer> numbers2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
		numbers2.add(10);

		System.out.println(numbers1.size());
		System.out.println(numbers1);
		System.out.println(numbers2);

		// 1. ArrayList to Array
		String array[] = new String[numbers2.size()];
		numbers2.toArray(array);

		System.out.println(Arrays.toString(array));

		// random access
		List<Integer> suncList = Collections.synchronizedList(numbers2);

	}

}
