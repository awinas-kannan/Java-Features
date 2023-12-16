package com.awinas.learning.collections.sorting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

//https://howtodoinjava.com/java-sorting-guide/
public class ArraySorting {

	public static void main(String[] args) {

		Integer[] numbers = new Integer[] { 15, 11, 9, 55, 47, 18, 520, 1123, 366, 420 };

		// ComparableTimSort
		Arrays.sort(numbers);
		System.out.println(Arrays.toString(numbers));

		System.out.println("*** Reverse order ***");

		Arrays.sort(numbers, Collections.reverseOrder());

		System.out.println(Arrays.toString(numbers));

		System.out.println("*** Sort based on Range***");
		Arrays.sort(numbers, 2, 6);

		// Print array to confirm
		System.out.println(Arrays.toString(numbers));

		System.out.println("Java 8 Parallel sorting");

		Arrays.parallelSort(numbers);

		System.out.println(Arrays.toString(numbers));

		Random rd = new Random();
		Integer[] normalArr = new Integer[100000];
		Integer[] ParallelArr = new Integer[100000];
		for (int i = 0; i < normalArr.length; i++) {
			int x = rd.nextInt();
			normalArr[i] = x;
			ParallelArr[i] = x;
		}

		LocalDateTime ltd = LocalDateTime.now();
		Arrays.sort(normalArr);
		System.out.println("Normal sorting -> " + Duration.between(ltd, LocalDateTime.now()).toMillis());

		ltd = LocalDateTime.now();
		Arrays.parallelSort(ParallelArr);
		System.out.println("Parallel sorting -> " + Duration.between(ltd, LocalDateTime.now()).toMillis());

	}

}
