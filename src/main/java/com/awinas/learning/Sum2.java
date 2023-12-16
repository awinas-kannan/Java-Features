package com.awinas.learning;

import java.util.List;

public class Sum2 {

	public static int minSum(List<Integer> num, int k) {
		int sum = 0;
		// O(k)
		for (int i = 0; i < k; i++) {
			int max = 0;
			int maxIdx = 0;
			// O(n)
//			for (int j = 0; j < num.size(); j++) {
//				if (num.get(j) > max) {
//					max = num.get(j);
//					maxIdx = j;
//				}
//			}
			
			
			int x = (max / 2) + (max % 2);
			num.remove(maxIdx);
			num.add(x);
		}
		for (Integer x : num) {
			sum = sum + x;
		}
		return sum;

	}

}
