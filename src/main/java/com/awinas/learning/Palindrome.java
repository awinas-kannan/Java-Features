package com.awinas.learning;

import java.util.HashMap;
import java.util.Map;

//LowesIndia

public class Palindrome {

	private static Map<Character, Integer> map = new HashMap<Character, Integer>();

	public static void main(String[] args) {
//		System.out.println(isPalindromePossible("MADAM"));
//
//		System.out.println(isPalindromePossible("MAAM"));
//
//		System.out.println(isPalindromePossible("ABCABC"));
//
//		System.out.println(isPalindromePossible("ABCA"));
		System.out.println("*************** MOHIT **************");
		if (isPalindromePossible("AABCDBCDBCDBCDKKK")) {
			System.out.println(getPossiblePalindrome());
		}
	}

	private static String getPossiblePalindrome() {

		String palindrom = new String();

		for (Map.Entry<Character, Integer> ent : map.entrySet()) {
			System.out.println(palindrom);
			System.out.println(ent.getKey());
			if (ent.getValue() % 2 == 1) {
				palindrom = palindrom.substring(0, palindrom.length() / 2) + ent.getKey()
						+ palindrom.substring((palindrom.length() / 2), palindrom.length());
				for (int i = 0; i < ent.getValue() - 1; i++) {
					if (i % 2 == 0) {
						palindrom = palindrom + ent.getKey();
					} else {
						palindrom = ent.getKey() + palindrom;
					}
				}

			} else {
				for (int i = 0; i < ent.getValue(); i++) {
					if (i % 2 == 0) {
						palindrom = palindrom + ent.getKey();
					} else {
						palindrom = ent.getKey() + palindrom;
					}
				}
			}
		}
		return palindrom;
	}

	private static boolean isPalindromePossible(String str) {

		for (Character c : str.toCharArray()) {
			map.put(c, map.get(c) == null ? 1 : map.get(c) + 1);
		}
		System.out.println(map);
		int countOne = 0;
		for (Map.Entry<Character, Integer> ent : map.entrySet()) {
			if (ent.getValue() % 2 == 1) {
				countOne++;
			}
		}

		if (countOne > 1) {
			return false;
		}

		return true;

	}

}
