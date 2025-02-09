package com.awinas.learning.leetcode;

public class Recursion {

	public static void main(String[] args) {
		System.out.println("Factorial Iterative " + factorialIterative(5));
		System.out.println("Factorial Recursion " + factoriaRecursion(5));
		System.out.println("Factorial Tail Recursion " + factorialTailRecursion(5));

	}

	public static int factorialIterative(int n) {
		int result = 1;
		for (int i = 1; i <= n; i++) {
			System.out.println(result + " * " + i + " = " + result * i);
			result *= i;
		}
		return result;
	}

	public static int factoriaRecursion(int n) {
		if (n == 1 || n == 0) {
			return 1;
		}
		return n * factoriaRecursion(n - 1);
	}

//	factorialHelper(5, 1)
//	factorialHelper(4, 5)
//	factorialHelper(3, 20)
//	factorialHelper(2, 60)
//	factorialHelper(1, 120)

	public static int factorialHelper(int n, int accumulator) {
		if (n == 0 || n == 1)
			return accumulator;
		return factorialHelper(n - 1, n * accumulator); // Tail recursion
	}

	public static int factorialTailRecursion(int n) {
		return factorialHelper(n, 1); // Initial call with accumulator = 1
	}

}
