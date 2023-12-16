package com.awinas.learning.interview.nielsen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MissingNumber {

	public static void main(String[] args) {

		List<Integer> l = Arrays.asList(20, 19, 18, 17, 15);

		Collections.sort(l);

		int sum = 0;

		for (int i : l) {
			sum = sum + i;
		}

		System.out.println(sum);
		int totalSum = 0;

		System.out.println(l.get(0));
		System.out.println(l.get(0) + l.size() + 1);
		for (int j = l.get(0); j <= (l.get(0) + l.size()); j++) {
			totalSum = totalSum + j;
		}
		System.out.println("tot " + totalSum);
		System.out.println("Missing Num " + (totalSum - sum));

	}

}
