package com.awinas.learning.leetcode.easy;

// 704 : https://leetcode.com/problems/binary-search/

/**
 * 
 * Approach Time Complexity Space Complexity Recursive Binary Search O(log N)
 * O(log N) (due to recursion stack) Iterative Binary Search O(log N) O(1) ✅
 * 
 * 
 * Iterative approach is better for large inputs (avoids recursion overhead). ✅
 * Recursive approach is cleaner but uses extra space.
 * 
 */

public class BinarySearch {

	public static void main(String[] args) {
		System.out.println(binarySearchOLogN(new int[] { 1, 3, 5, 6 }, 5));
		System.out.println(binarySearchOLogN(new int[] { 1, 3, 5, 6 }, 2));
		System.out.println(binarySearchOLogN(new int[] { 1, 3, 5, 6 }, 7));
		System.out.println("*************************************");
		int[] nums = new int[] { 1, 3, 5, 6 };
		System.out.println(binarySearchRecursion(nums, 5, 0, nums.length - 1));
		System.out.println(binarySearchRecursion(nums, 2, 0, nums.length - 1));
		System.out.println(binarySearchRecursion(nums, 7, 0, nums.length - 1));
		System.out.println("*************************************");
		System.out.println(binarySearchRecursion2(nums, 5, 0, nums.length - 1));
		System.out.println(binarySearchRecursion2(nums, 2, 0, nums.length - 1));
		System.out.println(binarySearchRecursion2(nums, 7, 0, nums.length - 1));
	}

	public static int binarySearchOLogN(int[] nums, int target) {
		int start = 0, end = nums.length - 1;
		while (start <= end) {
			int midNumber = start + ((end - start) / 2);
			if (nums[midNumber] == target) {
				return midNumber;
			} else if (nums[midNumber] < target) {
				start = midNumber + 1;
			} else {
				end = midNumber - 1;
			}
		}
		return -1;
	}

	public static int binarySearchRecursion(int[] nums, int target, int start, int end) {

		if (start <= end) {
			int midNumber = start + ((end - start) / 2);
			if (nums[midNumber] == target) {
				return midNumber;
			} else if (nums[midNumber] < target) {
				start = midNumber + 1;
				return binarySearchRecursion(nums, target, start, end);
			} else {
				end = midNumber - 1;
				return binarySearchRecursion(nums, target, start, end);
			}
		}
		return -1;
	}

	public static int binarySearchRecursion2(int[] nums, int target, int start, int end) {
		if (start > end) {
			return -1;
		}
		int midNumber = start + ((end - start) / 2);
		if (nums[midNumber] == target) {
			return midNumber;
		} else if (nums[midNumber] < target) {
			start = midNumber + 1;
			return binarySearchRecursion2(nums, target, start, end);
		} else {
			end = midNumber - 1;
			return binarySearchRecursion2(nums, target, start, end);
		}
	}

}
