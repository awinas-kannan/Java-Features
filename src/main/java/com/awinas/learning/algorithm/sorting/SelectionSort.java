package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

/*
 * 	Finds the minimum element index and places it in the correct position iteratively.
 * 
 * 	Time Complexity: O(n²)
 * 	Best for: Small datasets.
 
 */
public class SelectionSort {

	public static void main(String[] args) {
		int arr[] = { 64, 34, 25, 12, 22, 11, 90, 0, -1, -2 };
		System.out.println(Arrays.toString(selectionSort(arr)));
	}

	private static int[] selectionSort(int[] array) {

		for (int i = 0; i < array.length - 1; i++) {
			int minIndex = i; // Assume the first element is the smallest

			// Find the index of the minimum element
			for (int j = i + 1; j < array.length; j++) {
				if (array[j] < array[minIndex]) {
					minIndex = j;
				}
			}

			swap(i, minIndex, array);
		}
		return array;
	}

	private static void swap(int i, int min_index, int[] arr) {
		if (i != min_index) {
			arr[i] = arr[i] + arr[min_index];
			arr[min_index] = arr[i] - arr[min_index];
			arr[i] = arr[i] - arr[min_index];
		}
	}

}
