package com.awinas.learning;

import java.util.Arrays;
import java.util.Random;


//Random num
public class RandomNm {

	public static void main(String[] args) {
		int[] intA = new int[25];

		Random rnd = new Random();
		rnd.nextInt(26);
		System.out.println(rnd.nextInt(26));

		boolean isFilled = true;
		do {
			isFilled = true;
			int randNUmber = rnd.nextInt(26);
			System.out.println(randNUmber);
			if (randNUmber != 0) {
				if (intA[randNUmber - 1] == 0) {
					intA[randNUmber - 1] = randNUmber;
				}

				for (int x : intA) {
					if (x == 0) {
						isFilled = false;
					}
				}
			} else {
				isFilled = false;
			}

		} while (isFilled != true);

		System.out.println(Arrays.toString(intA));

	}

}
