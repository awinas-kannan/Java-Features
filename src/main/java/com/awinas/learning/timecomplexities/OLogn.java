package com.awinas.learning.timecomplexities;

//There is no relation between time compexity & 
//no of iteration it takes
public class OLogn {
	public static void main(String[] args) {
		int n = 24;
		System.out.println(log2(n));
		int j = 1;
		for (int i = 1; i < n; i = i * 2) {
			System.out.println("Value of I " + i);
			System.out.println("Iter " + j);
			j++;
		}
	}

	public static double log2(int N) {
		return (Math.log(N) / Math.log(2));
	}
}
