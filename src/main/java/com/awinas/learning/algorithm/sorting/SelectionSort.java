package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

public class SelectionSort {

	public static void main(String[] args) {
		int arr[] = { 64, 34, 25, 12, 22, 11, 90, 0 };
		int n = arr.length;
		for (int i = 0; i < n - 1; i++) {
			int min_index = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[min_index] > arr[j]) {
					min_index = j;
				}
			}

//			Works
//			for (int j = i ; j < n-1; j++) {
//				if (arr[min_index] > arr[j+1]) {
//					min_index = j+1;
//				}
//			}

			swap(i, min_index, arr);
		}

		System.out.println(Arrays.toString(arr));
	}

	private static void swap(int i, int min_index, int[] arr) {
		if (i != min_index) {
			arr[i] = arr[i] + arr[min_index];
			arr[min_index] = arr[i] - arr[min_index];
			arr[i] = arr[i] - arr[min_index];
		}
	}

}
