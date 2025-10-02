package com.awinas.learning.leetcode.easy;

import java.util.Arrays;

public class S26_RemoveDuplicetsFromSortedArray {

	public static void main(String[] args) {
		System.out.println("S26_RemoveDuplicetsFromSortedArray");
		System.out.println(removeDuplicates(new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 }));
	}

	public static int removeDuplicates(int[] nums) {
		int openIndex = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[openIndex - 1] != nums[i]) {
				nums[openIndex] = nums[i];
				openIndex++;

			}
		}
		System.out.println(Arrays.toString(nums));
		return openIndex;
	}
}
