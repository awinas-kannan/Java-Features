package com.awinas.learning.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

	public static void main(String[] args) {

		Integer[] nums = new Integer[] { 2, 4, 6, 8 };
		int target = 12;

		System.out.println(Arrays.toString(getCombination(nums, target)));
	}

	private static int[] getCombination(Integer[] nums, int target) {
		int[] values = new int[2];
		Map<Integer, Integer> maps = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int remainingValue = target - nums[i];
			if (maps.get(remainingValue) != null) {
				values[0] = nums[i];
				values[1] = remainingValue;
			}
			maps.put(nums[i], i);
		}

		return values;

	}

}
