package com.awinas.learning.collections.sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//https://howtodoinjava.com/java/sort/sort-arraylist-strings-integers/
public class ListSorting {

	public static void main(String[] args) {
		List<String> names = Arrays.asList("Alex", "Charles", "Brian", "David");

		Collections.sort(names, (a, b) -> {
			return a.charAt(0) > b.charAt(0) ? 1 : -1;
		});

		names.forEach(System.out::println);
		System.out.println(Arrays.toString(names.toArray()));

		names.sort((a, b) -> a.charAt(0) > b.charAt(0) ? -1 : 1);

		names.forEach(System.out::println);

		System.out.println("\"######  Java 8 Sort");

		// 1. Natural order
		names.sort(Comparator.comparing(String::toString));

		System.out.println(names);

		// 2. Reverse order
		names.sort(Comparator.comparing(String::toString).reversed());

		System.out.println(names);

		System.out.println("###### Sort arraylist of strings with Java 8 stream");
		// 1. Natural order
		names.stream().sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList());
		List<String> sortedNames = names.stream().sorted(Comparator.comparing(String::toString))
				.collect(Collectors.toList());

		System.out.println(sortedNames);

		// 2. Reverse order
		List<String> reverseSortedNames = names.stream().sorted(Comparator.comparing(String::toString).reversed())
				.collect(Collectors.toList());

		System.out.println(reverseSortedNames);

	}

}
