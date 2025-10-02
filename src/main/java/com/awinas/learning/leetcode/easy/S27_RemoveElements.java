package com.awinas.learning.leetcode.easy;

import java.util.Arrays;

/*
 * Two Pointer Approach
 */
public class S27_RemoveElements {

	public static void main(String[] args) {
		System.out.println("S27_RemoveElements");
		System.out.println(removeElements(new int[] { 3, 2, 2, 3 }, 3));
		System.out.println(removeElements(new int[] { 0, 1, 2, 2, 3, 0, 4, 2 }, 2));

	}

	public static int removeElements(int[] nums, int val) {
		int openPosition = 0; // pointer for new array length
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != val) {
				nums[openPosition] = nums[i];
				openPosition++;
			}
		}
		System.out.println(Arrays.toString(nums));
		return openPosition; // new length
	}
}
