package com.awinas.learning.leetcode;


//713 : https://leetcode.com/problems/subarray-product-less-than-k/description/
	
// Sliding Window (Two Pointers)
public class SubArrayProductLessThanK {
	public static void main(String[] args) {
		bruteForce();
		optimizedSlidingWindow();
	}

	// O(n2)
	private static void bruteForce() {
		int[] nums = { 10, 5, 2, 6 };
		int k = 100;
		int count = 0;
		for (int i = 0; i < nums.length; i++) {
			int product = 1;
			for (int j = i; j < nums.length; j++) {
				product = product * (nums[j]);
				if (product < k) {
					count++;
				} else {
					System.out.println("breaking " + i + " j " + j);
					break;
				}
			}
		}
		System.out.println("bruteForce Count " + count);
	}

	// right - left + 1: This calculates the number of elements between the right
	// and left values,
	// inclusive of both the right and left values.
	
	
	// O(n) // O(1)
	
	// 2 2 10 10
	private static void optimizedSlidingWindow() {
		int[] nums = { 10, 5, 2, 6 ,1 };
		int k = 100;
		int count = 0;
		int product = 1;
		int left = 0;

		for (int right = 0; right < nums.length; right++) {
			product *= nums[right];

			while (product >= k && left <= right) { // 2 2 10 10
				product /= nums[left];
				left++;
			}

			count += right - left + 1;
		}

		System.out.println("optimizedSlidingWindow Count " + count);

	}

}

// 10, 5, 2, 6

// 10 + 1
// 10 5 + 2
// 10 5 2 //
// 5
// 5 2 + 2
// 5 2 6 + 3
// 2
// 2 6
// 6

// 10 (0 -0 ) + 1 = 1 (10)
// 10 5 (1-0) +1 = 2 (10 5 , 5)
// 10 5 2 (failed) 
// 5 2 (3 -2 ) +1 = 2 (52, 2)
// 5 2 6 (4 - 2) + 1 = 3 ( 526, 26,6)  
// 5 2 6 1 ///(4-1) = 3 + 1 = 4  (5261,261,61,1)
/////
/**
 * 10
 * 10 5
 * 5 2
 * 5 2 6
 * 5 2 6 1
 */
