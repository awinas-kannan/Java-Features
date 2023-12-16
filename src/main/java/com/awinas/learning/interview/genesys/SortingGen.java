package com.awinas.learning.interview.genesys;

public class SortingGen {

	public static void main(String[] args) {
		String str = "genesys";
		char[] strArr = str.toCharArray();
		int length = strArr.length;
		System.out.println(length);

		for (int i = 0; i < length - 1; i++) {
			for (int j = 0; j < length - 1 - i; j++) {
				if (strArr[j] > strArr[j + 1]) {
					char temp = strArr[j];
					strArr[j] = strArr[j + 1];
					strArr[j + 1] = temp;
				}
			}
		}

		System.out.println(strArr);

	}

}
