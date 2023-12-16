package com.awinas.learning;

import java.util.Random;

public class RandomStringGenerator {

	private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(Math.random() * i);
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(generateRandomString());
		}
	}

	public static String generateRandomString() {
		int randomNumber = getRandomNumber();
		StringBuilder randomString = new StringBuilder();
		while (randomNumber > 0) {
			int index = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			randomString.append(ALPHA_NUMERIC_STRING.charAt(index));
			randomNumber--;
		}
		return randomString.toString();

	}

	private static int getRandomNumber() {
		Random r = new Random();
		int low = 5;
		int high = 11;
		int result = r.nextInt(high - low) + low;
		System.out.println("Resuult " + result);
		return result;
	}
}
