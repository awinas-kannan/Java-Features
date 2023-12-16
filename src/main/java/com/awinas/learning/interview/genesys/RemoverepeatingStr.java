package com.awinas.learning.interview.genesys;

public class RemoverepeatingStr {

	public static void main(String[] args) {
		String str = "massacheutesees";

		boolean isPresent = false;

		do {
			System.out.println("**********");
			isPresent = false;
			for (int i = 0; i < str.length() - 1; i++) {
				System.out.println(i);
				if (str.charAt(i) == str.charAt(i + 1)) {
					isPresent = true;
					String a = str.substring(0, i);
					String b = str.substring(i + 2, str.length());
					System.out.println(a + " " + b);
					str = a + b;
					break;
				}

			}
		} while (isPresent);

		System.out.println(str);
	}

}
