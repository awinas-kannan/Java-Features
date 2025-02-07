package com.awinas.learning.leetcode.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//https://leetcode.com/problems/two-sum/description/
public class TwoSum {

	public static void main(String[] args) {

		int[] nums = { 2, 7, 11, 15 };
		int target = 9;
		System.out.println(Arrays.toString(bruteForce(nums, target)));
		System.out.println(Arrays.toString(optimizedMethod(nums, target)));
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

}
