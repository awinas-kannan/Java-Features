package com.awinas.learning.leetcode;

import java.util.Arrays;

//https://www.geeksforgeeks.org/sort-array-converting-elements-squares/

public class SquaresOfSortedArrayTwoPtr {

	public static void main(String[] args) {
		int arr[] = { -6, -3, -1, 2, 4, 5 };
		sortedSquares(arr);
	}

	// Two pointer technique

	// Taking highest number then placing it last in
	// new temp array
	public static int[] sortedSquares(int[] nums) {
		int length = nums.length;
		int l = 0;
		int r = length - 1;
		int[] result = new int[length];

		for (int i = length - 1; i >= 0; i--) {

			if (Math.abs(nums[l]) > Math.abs(nums[r])) {
				result[i] = nums[l] * nums[l];
				l++;
			} else {
				result[i] = nums[r] * nums[r];
				r--;
			}
		}

		System.out.println(Arrays.toString(result));
		return result;
	}

}
