package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

public class InsertionSort {

	public static void main(String[] args) {

		int arr[] = { 64, 34, 25, 12, 22, 11, 90, 0 };

		System.out.println(Arrays.toString(insertionSort(arr)));
		System.out.println(Arrays.toString(insertionSort(new int[] { 12, 11, 13, 5, 6 })));

		System.out.println(Arrays.toString(insertionSort_v2(arr)));
		System.out.println(Arrays.toString(insertionSort_v2(new int[] { 12, 11, 13, 5, 6 })));

	}

	/*
	 * Start from second number
	 * 
	 * Check from 0 index till Current number index
	 * 
	 * If number at i (fixed ) is greater the number at j, then move the number at i
	 * to j'th position shift all the numbers from j'th position till i
	 * 
	 */

	private static int[] insertionSort(int[] array) {
		// Start from second number
		for (int i = 1; i < array.length; i++) {
			// Check from 0 index till Current number index
			for (int j = 0; j < i; j++) {
				if (array[i] < array[j]) {
					shift(i, j, array);
					break;
				}
			}

		}
		return array;
	}

	private static void shift(int i, int j, int[] arr) {
		int temp = arr[i];
		for (int x = i; x > j; x--) {
			arr[x] = arr[x - 1];
		}
		arr[j] = temp;

	}

	/*
	 * Shifts the elements from left to right to i'th index
	 * 
	 */

	private static int[] insertionSort_v2(int[] array) {
		// Start from second number
		for (int i = 1; i < array.length; i++) {
			int key = array[i]; // The element to be inserted
			int j = i - 1;

			// Move elements that are greater than 'key' one position ahead
			while (j >= 0 && array[j] > key) {
				array[j + 1] = array[j];
				j--;
			}

			// Insert the key at its correct position
			array[j + 1] = key;
		}
		return array;
	}

}
