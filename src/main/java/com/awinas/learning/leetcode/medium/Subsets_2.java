package com.awinas.learning.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subsets_2 {

	public static void main(String[] args) {
		System.out.println(subsetsWithDup(new int[] { 1, 2, 2 }));
		System.out.println(subsetsWithDup(new int[] { 4, 4, 4, 1, 4 }));
	}

	public static List<List<Integer>> subsetsWithDup(int[] nums) {
		// important because will fail in (powerset.contains(setTemp)) if the input
		// arrays is suffled

		Arrays.sort(nums);
		// Remove the above line to check the duplicates in second input

		List<List<Integer>> powerSet = new ArrayList<>();
		powerSet.add(new ArrayList<>());

		for (int i = 0; i < nums.length; i++) {
			List<List<Integer>> powersetTemp = new ArrayList<>(powerSet);
			for (List<Integer> set : powersetTemp) {
				List<Integer> subSet = new ArrayList<>(set);
				subSet.add(nums[i]);
				if (!powerSet.contains(subSet)) {
					powerSet.add(subSet);
				}
			}
		}

		return powerSet;
	}
}
