package com.awinas.learning.interview.genesys;

public class SortingGenQsort {

	public static void main(String[] args) {
		String str = "genesys";
		char[] charArr = str.toCharArray();
		quickSort(charArr, 0, charArr.length - 1);
	}

	public static int partition(char[] charArr, int start, int end) {

		int index = start - 1;
		char pivot = charArr[end];
		for (int i = start; i < end; i++) {
			if (charArr[i] < pivot) {
				index++;
				char temp = charArr[i];
				charArr[i] = charArr[index];
				charArr[index] = temp;
			}
		}
		char temp = charArr[end];
		charArr[end] = charArr[index + 1];
		charArr[index + 1] = temp;
		System.out.println(charArr);
		return index + 1;

	}

	public static void quickSort(char[] charArr, int start, int end) {

		if (start < end) {
			int partition = partition(charArr, start, end);
			quickSort(charArr, start, partition - 1);
			quickSort(charArr, partition + 1, end);
			System.out.println(charArr);
		}

	}

}
