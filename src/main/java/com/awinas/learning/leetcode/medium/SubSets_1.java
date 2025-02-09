package com.awinas.learning.leetcode.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

// 78 : https://leetcode.com/problems/subsets/

// The number of subsets of a set with n is 2^n
// This includes:
// All possible selections of elements. 
// The empty subset, which contains no elements.

/*
 *  1 2 3 4
 *  
 *  []
 *  
 *  1
 * 
 *  2
 *  1 2
 *  
 *  3
 *  1 3
 *  2 3
 *  1 2 3
 *  
 *  4 
 *  1 4
 *  2 4
 *  1 2 4
 *  3 4
 *  1 3 4
 *  2 3 4
 *  1 2 3 4
 *  
 */

/*  1 2 3 4
 *  2*2*2*2 (Each element has 2 choices) It can be or cannot be part of sub set.. 2^n
 */

public class SubSets_1 {

	public static void main(String[] args) {
		System.out.println(subsets(IntStream.of(1, 2, 3, 4).toArray()));
		System.out.println(subsetsBT(IntStream.of(1, 2, 3, 4).toArray()));
	}

	// Time Complexity : O(n * 2^n) (Each element has 2 choices: include or
	// exclude).
	// Space Complexity : O(2^n)
	public static List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> powerSet = new ArrayList<>();
		powerSet.add(new ArrayList<>());

		for (int i = 0; i < nums.length; i++) {
			List<List<Integer>> powersetTemp = new ArrayList<>();

			for (List<Integer> set : powerSet) {
				List<Integer> newL = new ArrayList<>(set);
				newL.add(nums[i]);
				powersetTemp.add(newL);
			}

			for (List<Integer> setTemp : powersetTemp) {
				powerSet.add(setTemp);
			}

		}

		return powerSet;
	}

	// More Optimised way... without second for loop
	// Time Complexity : O(n * 2^n) (Each element has 2 choices: include or
	// exclude).
	// Space Complexity : O(2^n)
	public static List<List<Integer>> subsets2(int[] nums) {
		List<List<Integer>> powerSet = new ArrayList<>();
		powerSet.add(new ArrayList<>());

		for (int i = 0; i < nums.length; i++) {
			List<List<Integer>> powersetTemp = new ArrayList<>(powerSet);
			for (List<Integer> set : powersetTemp) {
				List<Integer> subset = new ArrayList<>(set);
				subset.add(nums[i]);
				powerSet.add(subset);
			}
		}

		return powerSet;
	}

	// Backtracking is a recursive approach where we explore all possible subsets by
	// either including or excluding each element.

	public static List<List<Integer>> subsetsBT(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		generate(0, nums, new ArrayList<>(), result);
		return result;
	}

	private static void generate(int index, int[] nums, List<Integer> current, List<List<Integer>> result) {
		result.add(new ArrayList<>(current)); // Store the current subset

		for (int i = index; i < nums.length; i++) {
			current.add(nums[i]); // Choose a number
			generate(i + 1, nums, current, result); // Move to next number
			current.remove(current.size() - 1); // Backtrack (undo choice)
		}
	}

}
