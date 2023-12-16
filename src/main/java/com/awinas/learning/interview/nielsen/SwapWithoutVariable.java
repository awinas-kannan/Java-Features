package com.awinas.learning.interview.nielsen;

public class SwapWithoutVariable {

	public static void main(String[] args) {
		int a = 10;
		int b = 5;
		a = a + b;
		b = a - b;
		a = a - b;
		System.out.println("A val " + a);
		System.out.println("B val " + b);

	}

}
