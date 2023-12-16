package com.awinas.learning.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subsets2 {

	public static void main(String[] args) {
		System.out.println(subsetsWithDup(new int[] { 1, 2, 2 }));
		System.out.println(subsetsWithDup(new int[] { 4, 4, 4, 1, 4 }));
	}

	public static List<List<Integer>> subsetsWithDup(int[] nums) {
		// important beacuse will fail in (powerset.contains(setTemp)) if the input
		// arrays in suffeled

		Arrays.sort(nums);
		// Remove the above line to check the duplicates in second input

		List<List<Integer>> powerset = new ArrayList<>();
		powerset.add(new ArrayList<>());

		for (int i = 0; i < nums.length; i++) {
			List<List<Integer>> powersetTemp = new ArrayList<>();
			for (List<Integer> set : powerset) {
				List<Integer> newL = new ArrayList<>(set);
				newL.add(nums[i]);
				powersetTemp.add(newL);
			}
			for (List<Integer> setTemp : powersetTemp) {

				// checks the oreder alose
				// will fails in suffeled one
				if (!powerset.contains(setTemp))
					powerset.add(setTemp);
			}

		}

		return powerset;
	}
}
