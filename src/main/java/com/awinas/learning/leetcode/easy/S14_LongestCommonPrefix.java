package com.awinas.learning.leetcode.easy;

public class S14_LongestCommonPrefix {

	public static void main(String[] args) {
		String[] str = new String[] { "flower", "flow", "flight" };
		System.out.println(longestCommonPrefix(str));
		System.out.println(longestCommonPrefix(new String[] { "flower", "flow", "flight" }));
		System.out.println(longestCommonPrefixWithoutTemp(new String[] { "flower", "flow", "flight" }));
		System.out.println("longestCommonPrefix -" + longestCommonPrefix(new String[] { "dog", "racecar", "car" }));

		System.out.println("longestCommonPrefix -"
				+ longestCommonPrefix_PrefixReduction(new String[] { "flower", "flow", "flight" }));
	}

	/*
	 * Assume Str [0] as Prefix Take the first String -> Iterate the other 2 String
	 * ->
	 */
	public static String longestCommonPrefix(String[] strs) {
		String prefix = strs[0];
		for (int i = 1; i < strs.length; i++) {
			String currentStr = strs[i];
			String tempPrefix = "";
			for (int j = 0; j < currentStr.length(); j++) {
				if (j < prefix.length() && currentStr.charAt(j) == prefix.charAt(j)) {
					tempPrefix = tempPrefix + currentStr.charAt(j);
				} else {
					break;
				}
			}
			prefix = tempPrefix;

		}
		return prefix;
	}

	public static String longestCommonPrefixWithoutTemp(String[] strs) {
		String prefix = strs[0];
		for (int i = 1; i < strs.length; i++) {
			String currentStr = strs[i];
			int j = 0;
			for (; j < currentStr.length(); j++) {
				if (!(j < prefix.length() && currentStr.charAt(j) == prefix.charAt(j))) {
					break;
				}
			}
			prefix = prefix.substring(0, j);
		}
		return prefix;
	}

	public static String longestCommonPrefix_PrefixReduction(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		String prefix = strs[0];
		for (int i = 1; i < strs.length; i++) {
			while (strs[i].indexOf(prefix) != 0) {
				prefix = prefix.substring(0, prefix.length() - 1);
				if (prefix.isEmpty()) {
					return "";
				}
			}
		}
		return prefix;
	}

}
