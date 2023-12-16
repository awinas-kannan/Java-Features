package com.awinas.learning.algorithm;

import java.util.Arrays;
import java.util.List;

public class VowelPresent {

	public static void main(String[] args) {
		List<Character> list = Arrays.asList('a','e','i','o','u');
		String str = "awinas";
		
		for(Character c : str.toCharArray()) {
			if(list.contains(c)) {
				System.out.println("Char -> " +c);
				System.out.println("vowel present");
				break;
			}
		}

	}

}
