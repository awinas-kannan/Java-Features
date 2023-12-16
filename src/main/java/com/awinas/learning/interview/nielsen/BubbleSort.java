package com.awinas.learning.interview.nielsen;

import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		System.out.println("Bubble sort");

		Integer[] arr = new Integer[] { 5, 4, 2, 3, 1 };
		int n = arr.length;
		System.out.println("count " + n);
		for (int i = 0; i < n - 1; i++) {

			for (int j = 0; j < n - 1 - i; j++) {

				if (arr[j] > arr[j + 1]) {
					// swap
					int x = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = x;
				}
			}
		}

		System.out.println("Arr");
		System.out.println(Arrays.toString(arr));
	}

}
