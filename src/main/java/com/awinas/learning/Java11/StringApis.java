package com.awinas.learning.Java11;

public class StringApis {

	public static void main(String[] args) {

		/*
		 * 3.1. String.repeat(Integer)
		 * 
		 */
		String str = "Hello ";
		str = str.repeat(5);
		System.out.println(str);

		/*
		 * Previously, we have been using it from Apache’s StringUtils.java.
		 */
		System.out.println("1".isBlank());// false
		System.out.println("".isBlank());// true
		System.out.println("    ".isBlank());// true

		/*
		 * 3.3. String.strip() This method takes care of removing leading and trailing
		 * white-spaces. We can be even more specific by removing just the leading
		 * characters by using String.stripLeading() or just the trailing characters by
		 * using String.stripTrailing().
		 */

		System.out.println("   hi  ".strip());
		System.out.println("   hi  .".stripLeading());
		System.out.println("   hi  ".stripTrailing());

		/*
		 * 3.4. String.lines() This method helps in processing multi-line texts as a
		 * Stream.
		 */

		String testString = "hello\nworld\nis\nexecuted";
		var linesList = testString.lines().toList();
		System.out.println(linesList);

	}
}
