package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

//isSwapped -> if there were no changes in iteration

public class BubbleSort {

	public static void main(String[] args) {
		int arr[] = { 64, 34, 25, 12, 22, 11, 90 };
		// int arr[] = { 11, 12, 22, 25, 34, 64, 90 };

		for (int i = 0; i < arr.length - 1; i++) {
			boolean isSwapped = false;
			System.out.println("Here");
			for (int j = 0; j < arr.length - 1 - i; j++) {
				if (arr[j] > arr[j + 1]) {
					isSwapped = true;
					arr[j] = arr[j] + arr[j + 1];
					arr[j + 1] = arr[j] - arr[j + 1];
					arr[j] = arr[j] - arr[j + 1];
				}
			}
			if (!isSwapped) {
				break;
			}

		}

		System.out.println(Arrays.toString(arr));
	}

}
