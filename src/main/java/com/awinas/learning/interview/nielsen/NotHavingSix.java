package com.awinas.learning.interview.nielsen;

//What is the probability of an integer from 1 to 60,000 not having the digit 6

//1 -100 (19)
// 600  - 699  // 1600 - 1699  //2600 - 2699 //3600 - 3699 --- // 9600 - 9699 ( 1000)
//6000 - 6999  //16000 - 16999 //26000 - 26999  --- // 56000 -56999  (6000)

//

//10070
public class NotHavingSix {
	public static void main(String[] args) {

		int count = 0;
		for (int i = 1; i <= 60000; i++) {
			if (String.valueOf(i).contains("6")) {
				// System.out.print(" " + i);
				count++;
			}
		}

		System.out.println("count -> " + count);
		int noDigWithoutSix = 60000 - count;
		System.out.println("noDigWithoutSix " + noDigWithoutSix);
		double prob = ((double) noDigWithoutSix / (double) 60000);
		System.out.println("probablity -> " + prob);

	}
}
