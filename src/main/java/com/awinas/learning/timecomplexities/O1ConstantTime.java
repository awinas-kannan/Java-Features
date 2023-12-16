package com.awinas.learning.timecomplexities;

public class O1ConstantTime {
	public static void main(String[] args) {
		int n = 1000;
		System.out.println("Hey - your input is: " + n);

		n = 1000;
		System.out.println("Hey - your input is: " + n);
		System.out.println("Hmm.. I'm doing more stuff with: " + n);
		System.out.println("And more: " + n);
	}
}

//The above example is also constant time. Even if it takes 3 times as long to run, 
//it doesn't depend on the size of the input, n. We denote constant time algorithms as follows: O(1). 
//Note that O(2), O(3) or even O(1000) would mean the same thing.