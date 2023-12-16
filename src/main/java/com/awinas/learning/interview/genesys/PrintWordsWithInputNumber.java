package com.awinas.learning.interview.genesys;

public class PrintWordsWithInputNumber {

	public static void main(String[] args) {

		String inputSentence = "one two three four five six seven eight nine ten";

		// while (!isExit) {
		int num = 5;
		printWordshavingNum(inputSentence, num);
		// }
	}

	private static void printWordshavingNum(String sentence, int num) {

		String[] strArr = sentence.split(" ");
		for (int i = 0; i < strArr.length; i++) {
			if (strArr[i].length() == num) {
				System.out.println(strArr[i]);
			}
		}
	}
}
