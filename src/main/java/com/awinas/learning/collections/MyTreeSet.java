package com.awinas.learning.collections;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MyTreeSet {

	public static void main(String[] args) {
		TreeSet<String> TreeSet = new TreeSet<>();

		TreeSet.add("A");
		TreeSet.add("B");
		TreeSet.add("C");
		TreeSet.add("D");
		TreeSet.add("E");

		System.out.println("Convert TreeSet to Array Example");

		String[] values = new String[TreeSet.size()];

		TreeSet.toArray(values);

		System.out.println(Arrays.toString(values));

		System.out.println("Convert TreeSet to ArrayList Example");

		List<String> valuesList = TreeSet.stream().collect(Collectors.toList());

		System.out.println(valuesList);

	}

}
