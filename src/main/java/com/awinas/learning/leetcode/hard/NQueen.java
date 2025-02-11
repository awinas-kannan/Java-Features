package com.awinas.learning.leetcode.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 51 : https://leetcode.com/problems/n-queens/\

//https://takeuforward.org/data-structure/n-queen-problem-return-all-distinct-solutions-to-the-n-queens-puzzle/

public class NQueen {

	public static void main(String[] args) {
//		System.out.println("Valid Positions " + solveNQueens(2));
//		System.out.println("Valid Positions " + solveNQueens(4));
//		System.out.println("Valid Positions " + solveNQueens(5));
//		System.out.println("Valid Positions " + solveNQueens(8));
//		System.out.println("Valid Positions " + solveNQueens(9));

		System.out.println("Valid Positions " + solveNQueensOptimised(8));
	}

	
	/*
	 * In brute force, we try placing N queens in an N×N board in every possible way.
	 * Each queen has N choices per row, leading to: O(N^N)
	 * 
	 *  Space Complexity : O(N^2) -> If storing the board for output.
	 */
	public static List<List<String>> solveNQueens(int n) {

		List<List<String>> results = new ArrayList<>();
		char[][] board = new char[n][n];

		for (char[] row : board) {
			Arrays.fill(row, '.');
		}

		long start = System.currentTimeMillis();
		placeQueens(board, 0, results, n);

		System.out.println("(solveNQueens) Time Taken : "
				+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start) + " Seconds");
		System.out.println("Board " + Arrays.deepToString(board));
		return results;
	}

	/**
	 * 
	 * Moving Row wise
	 * 
	 */

	private static void placeQueens(char[][] board, int row, List<List<String>> results, int n) {
		if (row == n) {
			if (isValidBoard(board, n)) {
				results.add(constructBoard(board));
			}
			return;
		}

		for (int col = 0; col < n; col++) {
			board[row][col] = 'Q';
			placeQueens(board, row + 1, results, n);
			board[row][col] = '.';
		}

	}

	private static List<String> constructBoard(char[][] board) {
		List<String> result = new ArrayList<>();
		for (char[] row : board) {
			System.out.println(row);
			result.add(new String(row));
		}
		System.out.println("********");
		return result;
	}

	private static boolean isValidBoard(char[][] board, int n) {
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (board[row][col] == 'Q') {

					// Check for another queen in the same column
					for (int k = 0; k < n; k++) {
						if (k != row && board[k][col] == 'Q')
							return false;
					}

					// Check upper-left diagonal
					for (int k = 1; row - k >= 0 && col - k >= 0; k++) {
						if (board[row - k][col - k] == 'Q')
							return false;
					}

					// Check upper-right diagonal
					for (int k = 1; row - k >= 0 && col + k < n; k++) {
						if (board[row - k][col + k] == 'Q')
							return false;
					}
				}
			}
		}
		return true;
	}

	/*
	 * Optimized backtracking avoids unnecessary checks, reducing runtime from
	 * O(N^N) to O(N!)
	 * Due to pruning (column & diagonal checks in O(1)), we reduce invalid placements.
	 * 
	 * Space Complexity : O(N^2) -> If storing the board for output.
	 */

	public static List<List<String>> solveNQueensOptimised(int n) {
		List<List<String>> results = new ArrayList<>();
		char[][] board = new char[n][n];
		for (char[] row : board)
			Arrays.fill(row, '.'); // Initialize board

		boolean[] cols = new boolean[n]; // Track used columns
		boolean[] topLeftToBottomRightDiagonal = new boolean[2 * n - 1]; // Main diagonals (\) --> row - col + (N - 1)
		boolean[] topRightToBottomLeftDiagonal = new boolean[2 * n - 1]; // Anti-diagonals (/) --> row + col
		long start = System.currentTimeMillis();
		backtrack(0, board, results, cols, topLeftToBottomRightDiagonal, topRightToBottomLeftDiagonal, n);
		System.out.println("(solveNQueensOptimised) Time Taken : "
				+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start) + " Seconds");

		return results;
	}

	private static void backtrack(int row, char[][] board, List<List<String>> results, boolean[] cols, boolean[] TLtoBR,
			boolean[] TRtoBL, int n) {

		if (row == n) { // Base case: All queens placed
			results.add(constructBoard(board));
			return;
		}

		for (int col = 0; col < n; col++) {
			if (cols[col] || TLtoBR[row - col + (n - 1)] || TRtoBL[row + col]) {
				continue; // Prune invalid placements
			}

			// Place queen
			board[row][col] = 'Q';
			cols[col] = TLtoBR[row - col + (n - 1)] = TRtoBL[row + col] = true;

			// Recur for next row
			backtrack(row + 1, board, results, cols, TLtoBR, TRtoBL, n);

			// Backtrack (remove queen)
			board[row][col] = '.';
			cols[col] = TLtoBR[row - col + (n - 1)] = TRtoBL[row + col] = false;
		}
	}

}
