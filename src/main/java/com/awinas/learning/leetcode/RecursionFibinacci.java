package com.awinas.learning.leetcode;

//0 1 1 2 3 5 8 13 21 34 55
//

/*
 * The Fibonacci series is defined as: F(n)=F(n−1)+F(n−2)
 * 
 * F(0)=0, F(1)=1
 * 
 * If n = 5
 * f(4) + f(3) = 2 + 1 = 3
 * 
 */

public class RecursionFibinacci {

	public static void main(String[] args) {
		System.out.println(fibnocciSeriesBruteForce(10));
		System.out.println(fibnocciSeriesRecursion(10));
		System.out.println(fibonacci(10));

	}

	// Time Complexity : O(n) --> Optimised Approach
	// Space Complexity : O(1) -> Uses only 3 integers (Constant space)
	public static int fibnocciSeriesBruteForce(int num) {

		if (num == 1)
			return 0;
		if (num == 2)
			return 1;

		int n_1 = 1;
		int n_2 = 0;
		int next = 0;
		for (int i = 3; i <= num; i++) {
			next = n_1 + n_2;
			n_2 = n_1;
			n_1 = next;
		}
		return next;
	}

	public static int fibnocciSeriesRecursion(int num) {

		if (num == 1)
			return 0;
		if (num == 2)
			return 1;

		return fibnocciSeriesRecursion_1(3, 1, 0, num);
	}

	static int fibnocciSeriesRecursion_1(int i, int n_1, int n_2, int num) {
		if (i > num) {
			return n_1;
		}
		int newNUm = n_1 + n_2;
		n_2 = n_1;
		n_1 = newNUm;
		return fibnocciSeriesRecursion_1(i + 1, n_1, n_2, num);
	}

	public static int fibonacci(int n) {
		if (n == 1)
			return 0;
		if (n == 2)
			return 1;
		return fibonacci(n - 1) + fibonacci(n - 2); // Recursive calls
	}

}
