package com.awinas.learning.leetcode.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// 20 : https://leetcode.com/problems/valid-parentheses/description/

public class S20_ValidParentheses {

	public static void main(String[] args) {

		System.out.println("isValid " + isValid("()"));
		System.out.println("isValid " + isValid("()[]{}"));
		System.out.println("isValid " + isValid("(]"));
		System.out.println("isValid " + isValid("([])"));
		System.out.println("isValid " + isValid(")"));
	}

	// ( [ {
	// ) ] }

	public static boolean isValid(String s) {
		if (s.length() % 2 != 0) {
			return false;
		}

		List<Character> list = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '{' || c == '[' || c == '(') {
				list.add(c);
			} else if (c == '}' && list.size() > 0 && list.get(list.size() - 1) == '{') {
				list.remove(list.size() - 1);
			} else if (c == ']' && list.size() > 0 && list.get(list.size() - 1) == '[') {
				list.remove(list.size() - 1);
			} else if (c == ')' && list.size() > 0 && list.get(list.size() - 1) == '(') {
				list.remove(list.size() - 1);
			} else {
				return false;
			}
		}

		return list.size() == 0;
	}

	// Time : O(n)
	// Space : O(n)

	public static boolean isValid2(String s) {
		if (s.length() % 2 != 0) {
			return false;
		}
		List<Character> list = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '{' || c == '[' || c == '(') {
				list.add(c);
			} else if (list.size() == 0) {
				return false;
			} else if ((c == '}' && list.get(list.size() - 1) == '{') || (c == ']' && list.get(list.size() - 1) == '[')
					|| (c == ')' && list.get(list.size() - 1) == '(')) {
				list.remove(list.size() - 1);
			} else {
				return false;
			}
		}

		return list.size() == 0;
	}

	// Stack-Based Approach
	// Time : O(n)
	// Space : O(n)
	public static boolean isValidStackBased(String s) {
		Stack<Character> stack = new Stack<>();
		for (Character ch : s.toCharArray()) {
			if (ch == '(' || ch == '{' || ch == '[') {
				stack.push(ch);
			} else {
				if (stack.isEmpty())
					return false; // ()}
				char top = stack.pop();
				if ((ch == ')' && top != '(') || (ch == '}' && top != '{') || (ch == ']' && top != '[')) {
					return false;
				}
			}
		}
		return stack.isEmpty();

	}

}
