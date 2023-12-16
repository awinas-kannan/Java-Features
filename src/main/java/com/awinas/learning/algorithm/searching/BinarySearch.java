package com.awinas.learning.algorithm.searching;

public class BinarySearch {

	public static void main(String[] args) {
		// Should have numbers in sorted order

		// int n = -1;
		// int n =100
		int n = 8;
		Integer[] numbers = new Integer[] { 1, 4, 6, 7, 9, 13, 15, 16, 18, 20, 21 };

		int pos = binarySearch(numbers, n);

		if (pos != -1) {
			System.out.println("Present at " + pos);
		} else {
			System.out.println("Not present");
		}
		System.out.println("***************** Recursion **************** ");

		int posx = binarySearch(numbers, n, 0, numbers.length - 1);
		if (posx != -1) {
			System.out.println("Present at " + posx);
		} else {
			System.out.println("Not present");
		}
	}

	private static int binarySearch(Integer[] numbers, int n) {
		int length = numbers.length;
		System.out.println("length " + length);
		int start = 0, end = length - 1;
		while (start <= end) {
			System.out.println("Start " + start + " End " + end);
			int midNUmber = start + ((end - start) / 2);
			System.out.println("midNUmber " + midNUmber);
			if (numbers[midNUmber] == n) {
				System.out.println("Present at " + midNUmber);
				return midNUmber;
			}

			if (n < numbers[midNUmber]) {
				end = midNUmber - 1;
			} else {
				start = midNUmber + 1;
			}
		}

		return -1;
	}

	private static int binarySearch(Integer[] numbers, int n, int start, int end) {

		if (start <= end) {
			System.out.println("Start " + start + " End " + end);
			int midNUmber = start + ((end - start) / 2);
			System.out.println("midNUmber " + midNUmber);
			if (numbers[midNUmber] == n) {
				System.out.println("Present at " + midNUmber);
				return midNUmber;
			}

			if (n < numbers[midNUmber]) {
				binarySearch(numbers, n, start, midNUmber - 1);
			} else {
				binarySearch(numbers, n, midNUmber + 1, end);
			}
		}

		return -1;
	}
}
