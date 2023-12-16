package com.awinas.learning;

public class LowesMissing {

	public static void main(String[] args) {
		Integer[] input = new Integer[] { 1, 2, 3, 4, 5, 7, 8 };
		int length = input.length;
		int sum = 0;
		// o(n)
		for (int i = 1; i <= length + 1; i++) {
			sum = sum + i;
		}
		// o(n)
		for (int i = 0; i < length; i++) {
			sum = sum - input[i];
		}

		System.out.println("Missing value " + sum);
	}

}



