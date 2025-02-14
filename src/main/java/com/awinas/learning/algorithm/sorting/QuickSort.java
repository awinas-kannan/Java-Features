package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

/*
 * https://www.geeksforgeeks.org/quick-sort/
 * 
 * 
 * Algorithm Steps
 * 	Choose a pivot (typically the last element, first element, or a random element).
 * 	Partition the array so that:
 * 		Elements smaller than the pivot go to the left.
 * 		Elements greater than the pivot go to the right.
 * 	Recursively apply Quick Sort to both partitions.
 * 	Base case: If the subarray has 0 or 1 element, it is already sorted.
 * 
 * 
 * 	Time complexity: O(n log n).
 * 
 * 	Auxiliary Space	: O(log n) (average) / O(n) (worst)	Space required for recursive calls (stack space).
 * 
 * 
 */

public class QuickSort {

	// Driver Code
	public static void main(String[] args) {
		int[] arr = { 30, 80, 40, 90, 50, 10, 70 };
		quickSort(arr, 0, arr.length - 1);
		System.out.println("Sorted array: " + Arrays.toString(arr));
	}

	private static void quickSort(int[] arr, int low, int high) {
		System.out.println("low " + low + " high " + high);
		if (low < high) {

			// Partition the array and get the pivot index
			int pivotIndex = partition(arr, low, high);

			// Separately sort elements before
			// partition and after partition
			quickSort(arr, low, pivotIndex - 1);
			quickSort(arr, pivotIndex + 1, high);
		}
	}

	/*
	 * This function takes last element as pivot, places the pivot element at its
	 * correct position in sorted array, and places all smaller (smaller than pivot)
	 * to left of pivot and all greater elements to right of pivot
	 */

	private static int partition(int[] arr, int low, int high) {

		int pivot = arr[high]; // Choose the last element as the pivot

		// Index of smaller element and
		// indicates the right position
		// of pivot found so far
		int i = (low - 1);

		for (int j = low; j <= high - 1; j++) {

			// If current element is smaller
			// than the pivot
			if (arr[j] < pivot) {

				// Increment index of
				// smaller element
				i++;
				swap(arr, i, j);
			}
		}
		swap(arr, i + 1, high);
		System.out.println(Arrays.toString(arr));
		return (i + 1);
	}

	// A utility function to swap two elements
	static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
