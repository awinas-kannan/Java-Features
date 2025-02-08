package com.awinas.learning.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

// 3 : https://leetcode.com/problems/longest-substring-without-repeating-characters/description/

public class LongestSubstringWithoutRepeatingCharacters {

	public static void main(String[] args) {

		System.out.println("abcabcbb " + bruteForce("abcabcbb"));
		System.out.println("pwwkew " + bruteForce("pwwkew"));
		System.out.println("bbbbb " + bruteForce("bbbbb"));
		System.out.println("au " + bruteForce("au"));
		System.out.println("aab " + bruteForce("aab"));

		System.out.println("pwwkew".lastIndexOf("w"));
		System.out.println("pwwkew".substring("pwwkew".lastIndexOf("w")));

		System.out.println("*******************************************");

		System.out.println("abcabcbb " + optimizedSlidingWindow("abcabcbb"));
		System.out.println("pwwkew " + optimizedSlidingWindow("pwwkew"));
		System.out.println("bbbbb " + optimizedSlidingWindow("bbbbb"));
		System.out.println("au " + optimizedSlidingWindow("au"));
		System.out.println("aab " + optimizedSlidingWindow("aab"));
		System.out.println("aab " + optimizedSlidingWindow("abba"));

	}

	public static int bruteForce(String s) {
		if (s.length() < 1) {
			return 0;
		}
		int count = 1;
		String subStr = String.valueOf(s.charAt(0));
		for (int right = 1; right < s.length(); right++) {
			if (subStr.contains(String.valueOf(s.charAt(right)))) {
				if (count < subStr.length()) {
					count = subStr.length();
				}
				subStr = subStr.substring(subStr.lastIndexOf(s.charAt(right)) + 1);
				subStr = subStr + s.charAt(right);
			} else {
				subStr = subStr + s.charAt(right);
				if (count < subStr.length()) {
					count = subStr.length();
				}
			}

		}

//		System.out.println("subStr " + subStr);
		return count;

	}

	// HashMap
	// Time : O(n)
	// Space :O(min(n,256))

	// abcabcbb

	// Left should always increase. SHould not go back
	// So Math.max(map.get(c) + 1, left);
	public static int optimizedSlidingWindow(String s) {
		int maxLength = 0;
		int left = 0;
		Map<Character, Integer> map = new HashMap<>();
		for (int right = 0; right < s.length(); right++) {
			Character c = s.charAt(right);
			if (map.containsKey(c)) {
				if (left < map.get(c) + 1) {
					left = map.get(c) + 1;
				}
//				left = Math.max(map.get(c) + 1, left); // "abba"
			}
			map.put(c, right);
			maxLength = Math.max(maxLength, right - left + 1);
		}
		return maxLength;
	}
}
