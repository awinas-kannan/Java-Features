package com.awinas.learning.leetcode.easy;

// 35 : https://leetcode.com/problems/search-insert-position/description/

public class SearchInsertPosition {

	public static void main(String[] args) {

		System.out.println(searchInsertON(new int[] { 1, 3, 5, 6 }, 5));
		System.out.println(searchInsertON(new int[] { 1, 3, 5, 6 }, 2));
		System.out.println(searchInsertON(new int[] { 1, 3, 5, 6 }, 7));
		System.out.println("********************");
		System.out.println(searchInsertLogN(new int[] { 1, 3, 5, 6 }, 5));
		System.out.println(searchInsertLogN(new int[] { 1, 3, 5, 6 }, 2));
		System.out.println(searchInsertLogN(new int[] { 1, 3, 5, 6 }, 7));
		System.out.println(searchInsertLogN(new int[] { 1, 2, 4, 5, 6 }, 7));

	}

	public static int searchInsertON(int[] nums, int target) {
		int i = 0;
		for (; i < nums.length; i++) {
			if (nums[i] == target || nums[i] > target) {
				return i;
			}
		}
		return i;
	}

	/*
	 * Binary Search runs in O(log N) time. Each step reduces the search space by
	 * half.
	 * 
	 * Space Complexity : O(1) → We use only a few extra variables (constant space)
	 */
	public static int searchInsertLogN(int[] nums, int target) {

		int start = 0, end = nums.length - 1;
		while (start <= end) {
			int midNUmber = start + ((end - start) / 2);
			if (nums[midNUmber] == target) {
				return midNUmber;
			} else if (nums[midNUmber] < target) {
				start = midNUmber + 1;
			} else {
				end = midNUmber - 1;
			}
		}
		System.out.println("Start " + start);
		System.out.println("End " + end);
		return start;
	}

}
