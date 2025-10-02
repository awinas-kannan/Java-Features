package com.awinas.learning.leetcode.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//https://leetcode.com/problems/two-sum/description/
public class S1_TwoSum {

	public static void main(String[] args) {

		int[] nums = { 2, 7, 11, 15 };
		int target = 9;
		System.out.println(Arrays.toString(bruteForce(nums, target)));
		System.out.println(Arrays.toString(optimizedMethod(nums, target)));
		System.out.println(Arrays.toString(twoPointerApproach(nums, target)));
	}

	private static int[] bruteForce(int[] nums, int target) {
//		## 1. Brute Force Approach (O(n²))
//		Approach: Use two nested loops to check all pairs.
//		Time Complexity: \(O(n^2)\) because we are checking all possible pairs.
//		Space Complexity: \(O(1)\) since we use no extra space.

		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length - 1; j++) {
				if (nums[i] + nums[j] == target) {
					return new int[] { i, j };
				}
			}
		}
		return new int[] { -1, -1 };
	}

	private static int[] optimizedMethod(int[] nums, int target) {
//		## 2. Optimized Approach using HashMap (O(n))
//		Approach: Use a HashMap to store numbers and their indices.
//		Time Complexity: \(O(n)\) since we traverse the array once.
//		Space Complexity: \(O(n)\) for storing values in the HashMap.

		// VALUE , INDEX

		Map<Integer, Integer> hashMap = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (hashMap.containsKey(target - nums[i])) {
				return new int[] { hashMap.get(target - nums[i]), i };
			} else {
				hashMap.put(nums[i], i);
			}
		}
		return new int[] { -1, -1 };
	}

	private static int[] twoPointerApproach(int[] nums, int target) {
//	(O(n log n))	
//		  1. Sort the array.
//		  2. Use two pointers (one at the start, one at the end) to find the sum.
//		  3. Move pointers based on the sum comparison.
//		- Time Complexity: \(O(n \log n)\) due to sorting.
//        Space Complexity: \(O(1)\) if sorting is done in place, otherwise \(O(n)\) for a new sorted array.

		int left = 0, right = nums.length - 1;
		while (left < right) {
			int sum = nums[left] + nums[right];
			if (sum == target) {
				return new int[] { left, right };
			} else if (sum < target) {
				left++;
			} else {
				right--;
			}
		}
		return new int[] { -1, -1 };
	}

}
