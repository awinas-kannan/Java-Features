package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

/*
 * https://www.geeksforgeeks.org/merge-sort/
 * 
 * Merge Sort (Divide and Conquer)
 * 	Divides the array into two halves, sorts each half, and merges them.
 * 	Time Complexity: O(n log n)
 * 	Best for: Large datasets, stable sorting.
 * 	
 */

public class MergeSort {

	public static void main(String[] args) {

		int arr[] = { 38, 27, 43, 3, 9, 82, 10 };
		mergeSort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));

//		arr = new int[] { 64, 34, 25, 12, 22, 11, 90 };
//		mergeSort(arr, 0, arr.length - 1);
//		System.out.println(Arrays.toString(arr));
//
//		int arr2[] = { 11, 12, 22, 25, 34, 64, 90, 100 };
//		mergeSort(arr2, 0, arr2.length - 1);
//		System.out.println(Arrays.toString(arr2));
	}

	private static void mergeSort(int[] arr, int left, int right) {
		if (left < right) {
			int mid = left + (right - left) / 2; // Find the middle point

			// Recursively divide the array into two halves
			mergeSort(arr, left, mid);
			mergeSort(arr, mid + 1, right);

			// Merge the sorted halves
			merge(arr, left, mid, right);
		}
	}

	static void merge(int[] arr, int left, int mid, int right) {
		int n1 = mid - left + 1;
		int n2 = right - mid;

		// Create temporary arrays
		int[] leftArray = new int[n1];
		int[] rightArray = new int[n2];

		// Copy data to temporary arrays
		System.arraycopy(arr, left, leftArray, 0, n1);
		System.arraycopy(arr, mid + 1, rightArray, 0, n2);

		// Merge the temporary arrays
		int i = 0, j = 0, k = left;
		while (i < n1 && j < n2) {
			if (leftArray[i] <= rightArray[j]) {
				arr[k] = leftArray[i];
				i++;
			} else {
				arr[k] = rightArray[j];
				j++;
			}
			k++;
		}

		// Copy remaining elements from leftArray (if any)
		while (i < n1) {
			arr[k] = leftArray[i];
			i++;
			k++;
		}

		// Copy remaining elements from rightArray (if any)
		while (j < n2) {
			arr[k] = rightArray[j];
			j++;
			k++;
		}
	}

}
