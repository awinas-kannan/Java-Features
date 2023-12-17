package com.awinas.learning.Java14;

public class TextBlocks14 {

	public static void main(String[] args) {
		String text = """
				Did you know \
				Java 14 \
				has the most features among\
				all non-LTS versions so far\
				""";

		String text2 = """
				line1
				line2 \s
				line3
				""";

		String text3 = "line1\nline2 \nline3\n";

		//text2 and text3 are equal.

		System.out.println(text);
		System.out.println(text2);
		System.out.println(text3);
	}
}
