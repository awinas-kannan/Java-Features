package com.awinas.learning.algorithm.sorting;

import java.util.Arrays;

public class InsertionSort {

	public static void main(String[] args) {

		int arr[] = { 64, 34, 25, 12, 22, 11, 90, 0 };
		// int arr[] = { 12, 11, 13, 5, 6 };
		int n = arr.length;

		// Start from second number
		for (int i = 1; i < n; i++) {
			// Check from 0 index till Current number index
			for (int j = 0; j < i; j++) {
				// If Our Current number is less than any of prev number
				// then move out current number to that position
				// then shift all the number from that pos to below
				if (arr[i] < arr[j]) {
					shift(i, j, arr);
					break;
				}
			}
		}

		System.out.println(Arrays.toString(arr));
	}

	private static void shift(int i, int j, int[] arr) {
		System.out.println("Current number " + arr[i]);
		System.out.println("Lesser than number " + arr[j]);
		System.out.println(" Start " + j + " End " + i);
		int temp = arr[i];
		for (int x = i; x > j; x--) {
			arr[x] = arr[x - 1];
		}
		arr[j] = temp;

		System.out.println(Arrays.toString(arr));
	}

}
