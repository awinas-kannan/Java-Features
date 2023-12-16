package com.awinas.learning.collections;

public class Array {

	public static void main(String[] args) {
		Integer[][] intArr = new Integer[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(" " + intArr[i][j]);
			}
			System.out.println();
		}

		System.out.println(intArr.length);

		int[][] intArr1 = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(" " + intArr1[i][j]);
			}
			System.out.println();
		}

		intArr[5][5] = 10;

	}

}
