package com.awinas.learning.timecomplexities;

public class OkNExponetial {
	public static void main(String[] args) {
		int n = 3;
		int k = 4;
		System.out.println("Math.pow(k, n) " + Math.pow(k, n));
		for (int i = 1; i <= Math.pow(k, n); i++) {
			System.out.println("Hey - I'm busy looking at: " + i);
		}
	}
}
