package com.awinas.learning.leetcode.easy;

/*
 * 

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000

I can be placed before V (5) and X (10) to make 4 and 9. 
X can be placed before L (50) and C (100) to make 40 and 90. 
C can be placed before D (500) and M (1000) to make 400 and 900.

 */
public class S13_RomanToInt {

	public static void main(String[] args) {
		System.out.println("III - " + romanToInt("III"));
		System.out.println("LVIII - " + romanToInt("LVIII"));
		System.out.println("MCMXCIV - " + romanToInt("MCMXCIV"));
	}

	public static int romanToInt(String s) {
		int value = 0;
		int strLenght = s.length();
		for (int i = 0; i < s.length(); i++) {

			if (s.charAt(i) == 'I') {
				if (i + 1 < strLenght && s.charAt(i + 1) == 'V') {
					value = value + 4;
					i++;
				} else if (i + 1 < strLenght && s.charAt(i + 1) == 'X') {
					value = value + 9;
					i++;
				} else {
					value = value + 1;
				}
			} else if (s.charAt(i) == 'V') {
				value = value + 5;
			} else if (s.charAt(i) == 'X') {
				if (i + 1 < strLenght && s.charAt(i + 1) == 'L') {
					value = value + 40;
					i++;
				} else if (i + 1 < strLenght && s.charAt(i + 1) == 'C') {
					value = value + 90;
					i++;
				} else {
					value = value + 10;
				}

			} else if (s.charAt(i) == 'L') {
				value = value + 50;
			} else if (s.charAt(i) == 'C') {
				if (i + 1 < strLenght && s.charAt(i + 1) == 'D') {
					value = value + 400;
					i++;
				} else if (i + 1 < strLenght && s.charAt(i + 1) == 'M') {
					value = value + 900;
					i++;
				} else {
					value = value + 100;
				}
			} else if (s.charAt(i) == 'D') {
				value = value + 500;
			} else if (s.charAt(i) == 'M') {
				value = value + 1000;
			}

		}

		return value;
	}

}
