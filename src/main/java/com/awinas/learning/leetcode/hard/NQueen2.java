package com.awinas.learning.leetcode.hard;

// 52 : https://leetcode.com/problems/n-queens-ii/

//https://takeuforward.org/data-structure/n-queen-problem-return-all-distinct-solutions-to-the-n-queens-puzzle/

public class NQueen2 {

	public static void main(String[] args) {
		System.out.println("Valid Positions " + solveNQueensOptimised(2));
		System.out.println("Valid Positions " + solveNQueensOptimised(4));
		System.out.println("Valid Positions " + solveNQueensOptimised(5));
		System.out.println("Valid Positions " + solveNQueensOptimised(8));
		System.out.println("Valid Positions " + solveNQueensOptimised(9));
	}

	public static int solveNQueensOptimised(int n) {

		boolean[] cols = new boolean[n]; // Track used columns
		boolean[] upperLeftDiagonal = new boolean[2 * n - 1]; // Main diagonals (\) --> row - col + (N - 1)
		boolean[] upperRightDiagonal = new boolean[2 * n - 1]; // Anti-diagonals (/) --> row + col

		return backtrack(0, cols, upperLeftDiagonal, upperRightDiagonal, n);
	}

	private static int backtrack(int row, boolean[] cols, boolean[] upperLeftDiagonal, boolean[] upperRightDiagonal,
			int n) {
		if (row == n) { // Base case: All queens placed
			return 1;
		}
		int count = 0;

		for (int col = 0; col < n; col++) {
			if (cols[col] || upperLeftDiagonal[row - col + (n - 1)] || upperRightDiagonal[row + col])
				continue; // Prune invalid placements

			cols[col] = upperLeftDiagonal[row - col + (n - 1)] = upperRightDiagonal[row + col] = true;

			count += backtrack(row + 1, cols, upperLeftDiagonal, upperRightDiagonal, n);

			cols[col] = upperLeftDiagonal[row - col + (n - 1)] = upperRightDiagonal[row + col] = false;
		}

		return count;
	}

}
