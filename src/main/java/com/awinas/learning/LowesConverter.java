package com.awinas.learning;

public class LowesConverter {

	public static void main(String[] args) {

		String input = "aBCdEFghi";
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			int ascii = input.charAt(i);
			if (ascii >= 97 && ascii <= 122) {
				output.append((char) (ascii - 32));
			} else if (ascii >= 65 && ascii <= 90) {
				output.append((char) (ascii + 32));
			}

		}
		System.out.println(output.toString());

	}

}
