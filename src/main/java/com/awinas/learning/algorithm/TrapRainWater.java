package com.awinas.learning.algorithm;

public class TrapRainWater {

	public static void main(String[] args) {
		int arr[] = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		int n = arr.length;

		System.out.println("Maximum water that " + "can be accumulated is " + findWater(arr, n));
	}

	static int findWater(int arr[], int n) {
		// initialize output
		int result = 0;

		// maximum element on left and right
		int left_max = 0, right_max = 0;

		// indices to traverse the array
		int low = 0, high = n - 1;

		while (low <= high) {
			System.out.println("*********************************************************");
			System.out.println("low -> " + low);
			System.out.println("high -> " + high);
			System.out.println("left_max -> " + left_max);
			System.out.println("right_max -> " + right_max);
			System.out.println("result -> " + result);
			if (arr[low] < arr[high]) {
				if (arr[low] > left_max) {
					left_max = arr[low];
				} else {
					// water on curr element =
					// max - curr
					result += left_max - arr[low];
				}
				low++;
			} else {
				if (arr[high] > right_max) {
					// update right maximum
					right_max = arr[high];
				} else {
					result += right_max - arr[high];
				}
				high--;
			}
		}

		return result;
	}

}
