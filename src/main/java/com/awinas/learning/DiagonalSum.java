package com.awinas.learning;

public class DiagonalSum {

	public static void main(String[] args) {

		// int[][] arr = new int[3][3];

		// int[][] arr = { { 1, 2, 3 }, { 1, 2, 3 }, { 2, 2, 2 } };

		int[][] arr = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

		int leftDiagonalSum = 0;
		int rightDiagonalSum = 0;
		int r = arr.length;
		for (int i = 0; i <  arr.length; i++) {
			leftDiagonalSum = leftDiagonalSum + arr[i][i];
			rightDiagonalSum = rightDiagonalSum + arr[i][--r];

		}

		System.out.println(leftDiagonalSum);
		System.out.println(rightDiagonalSum);
	}

}
