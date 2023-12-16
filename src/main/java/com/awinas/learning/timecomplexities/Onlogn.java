package com.awinas.learning.timecomplexities;

public class Onlogn {

	public static void main(String[] args) {
		int n = 100;
		int x = 1;
		System.out.println("log2n  " + log2(n));
		System.out.println("nlogn  " + n * log2(n));
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j < n; j = j * 2) {
				System.out.println("Value of " + i + " and " + j);
				x++;
			}
		}

	}

	public static double log2(int N) {
		return (Math.log(N) / Math.log(2));
	}

}
