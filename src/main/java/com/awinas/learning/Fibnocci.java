package com.awinas.learning;

public class Fibnocci {

	public static void main(String[] args) {

		int nums = 25;

		fibnocciSerins(nums);

	}

	public static void fibnocciSerins(int num) {

		if (num == 1) {
			System.out.println(0);
			return;

		}
		if (num == 2) {
			System.out.println(1);
			return;
		}

		System.out.println(0);
		System.out.println(1);

		int prev = 0;
		int cur = 1;
//		for (int i = 3; i <= num; i++) {
//			int newNUm = cur + prev;
//			System.out.println(newNUm);
//			prev = cur;
//			cur = newNUm;
//
//		}

		fibRecursion(3, cur, prev, num);
	}

	static void fibRecursion(int x, int cur, int prev, int num) {
		if (x > num) {
			return;
		}
		int newNUm = cur + prev;
		System.out.println(newNUm);
		prev = cur;
		cur = newNUm;
		fibRecursion(x + 1, cur, prev, num);
	}
}
