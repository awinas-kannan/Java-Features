package com.awinas.learning.interview.genesys;

//
public class Permutation {

	public static void main(String[] args) {
		String word = "abcd";

//		Set<String> set = new LinkedHashSet<>();
//		set.add(word);
//		for (int i = 0; i < word.length(); i++) {
//			for (int j = 0; j < word.length(); j++) {
//				if (i != j) {
//					String wrd = swap(word, i, j);
//					set.add(wrd);
//				}
//			}
//		}
//
//		System.out.println(set);

		permutation(word);

	}

	private static String swap(String word, int i, int j) {
		System.out.println(i + " " + j);
		// System.out.println(word.substring(i, j - 1));
		char[] charArr = word.toCharArray();
		char temp = charArr[j];
		charArr[j] = charArr[i];
		charArr[i] = temp;
		return new String(charArr);

	}

	public static void permutation(String str) {
		permutation("", str);
	}

	private static void permutation(String prefix, String str) {
		System.out.println("-----------------------------");
		int n = str.length();
		if (n == 0)
			System.out.println("<<<<<<<print>>>>>> " + prefix);
		else {
			for (int i = 0; i < n; i++) {
				System.out.println("i -> " + i);
				System.out.println("Prefix - > " + prefix + str.charAt(i));
				System.out.println("STR -> " + str.substring(0, i) + str.substring(i + 1, n));
				permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
			}
		}
	}

}
