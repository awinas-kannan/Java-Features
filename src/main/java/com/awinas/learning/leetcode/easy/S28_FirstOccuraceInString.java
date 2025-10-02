package com.awinas.learning.leetcode.easy;

/*
 * Two Pointer Approach
 */
public class S28_FirstOccuraceInString {

	public static void main(String[] args) {
		System.out.println("S28_FirstOccuraceInString");
		System.out.println(firstOccurace4MS("sadbutsad", "sad"));
		System.out.println(firstOccurace4MS("leetcode", "leeto"));

	}

	public static int firstOccurace4MS(String haystack, String needle) {
//		System.out.println(haystack.length());
//		System.out.println(needle.length());
		for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
			System.out.println(haystack.substring(i, i + needle.length()));
			if (haystack.substring(i, i + needle.length()).equals(needle)) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * Time: O(n·m) worst case, but better in practice.
	 * Space: O(1)
	 * 
	 */
	public static int firstOccuraceManual(String haystack, String needle) {
		int n = haystack.length();
		int m = needle.length();

		for (int i = 0; i < n - m + 1; i++) {
			int j = 0;
			while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
				j++;
			}
			if (j == m)
				return i;
		}

		return -1;
	}

	public static int firstOccurace0MS(String haystack, String needle) {
		if (!haystack.contains(needle))
			return -1;
		return haystack.indexOf(needle);
	}
}
