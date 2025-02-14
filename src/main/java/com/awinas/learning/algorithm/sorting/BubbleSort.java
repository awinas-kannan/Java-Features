package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

/*
 * Move the Largest element to the last
 *  
 * Repeatedly swaps adjacent elements if they are in the wrong order.
 * 		Time Complexity: O(n²)	
 * 		Best for: Small datasets, easy to implement.
 * 
 * Optimized Version
 * 		The swapped flag is used to stop the algorithm if no swaps occur in a full pass (indicating the array is already sorted).
 * 		Reduces unnecessary iterations in best-case scenarios.
 * 
 * 	
 */

public class BubbleSort {

	public static void main(String[] args) {
		int arr[] = { 64, 34, 25, 12, 22, 11, 90 };

		System.out.println(Arrays.toString(bubbleSort(arr)));
		System.out.println(Arrays.toString(bubbleSort(new int[] { 11, 12, 22, 25, 34, 64, 90, 100 })));
	}

	private static int[] bubbleSort(int[] array) {

		for (int i = 0; i < array.length - 1; i++) {
			boolean isSwapped = false;
			System.out.println("Loop " + (i + 1));
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j] > array[j + 1]) {
					isSwapped = true;
					array[j] = array[j] + array[j + 1];
					array[j + 1] = array[j] - array[j + 1];
					array[j] = array[j] - array[j + 1];
				}
			}

			// If no swaps happened, the array is already sorted
			if (!isSwapped) {
				System.out.println("Fully Sorted");
				break;
			}

		}
		return array;
	}

}
