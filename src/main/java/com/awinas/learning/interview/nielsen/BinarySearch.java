package com.awinas.learning.interview.nielsen;

public class BinarySearch {

	public static void main(String[] args) {
		int x = 5;
		Integer[] arr = new Integer[] { 1, 3, 5, 6, 7, 8, 10 };
		int n = arr.length;

		int position = binarySearch(x, arr, n);
		if (position != -1) {
			System.out.println("Element present  at  " + position);
		} else {
			System.out.println("Element not present ");
		}
	}

	private static int binarySearch(int x, Integer[] arr, int n) {
		int start = 0;
		int end = n - 1;

		while (start <= end) {

			int midNumber = start + ((end - start) / 2);
			if (arr[midNumber] == x) {
				return midNumber;
			}

			if (x < arr[midNumber]) {
				end = midNumber - 1;
			} else if (x > arr[midNumber]) {
				start = midNumber + 1;
			}

		}

		return -1;
	}

}
