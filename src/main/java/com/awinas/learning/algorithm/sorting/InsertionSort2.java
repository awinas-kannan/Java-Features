package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

public class InsertionSort2 {

	public static void main(String[] args) {

		int arr[] = { 64, 34, 25, 12, 22, 11, 90, 0 };
		// int arr[] = { 12, 11, 13, 5, 6 };
		// int arr[] = { 1, 2, 3, 4, 5, 6, 7 };
		int n = arr.length;

		// Start from second number
		for (int i = 1; i < n; i++) {
			int current_num = arr[i];
			for (int j = i - 1; j >= 0; j--) {
				if (current_num < arr[j]) {
					swap(j + 1, j, arr);
				}
			}
		}

		System.out.println(Arrays.toString(arr));
	}

	private static void swap(int i, int j, int[] arr) {
		if (i != j) {
			arr[i] = arr[i] + arr[j];
			arr[j] = arr[i] - arr[j];
			arr[i] = arr[i] - arr[j];
		}
	}

}
