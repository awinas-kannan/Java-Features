package com.awinas.learning.algorithm;

import java.util.HashSet;



public class UniqueWordGenerator {
	  private static final char[] validChars = "selvi".toCharArray();
//	  private static final char[] validChars = "awi".toCharArray();

	    public static void main(String[] args) {
	        HashSet<String> uniqueWords = new HashSet<>();
	        generateUniqueWords("", 0, uniqueWords);
//	        for (String word : uniqueWords) {
//	            System.out.println(word);
//	        }
	        System.out.println(uniqueWords.size());
	    }

	    private static void generateUniqueWords(String word, int index, HashSet<String> uniqueWords) {
	        if (index == 5) {
	        	System.out.println(word);
	            uniqueWords.add(word);
	            return;
	        }
	        for (char c : validChars) {
	            generateUniqueWords(word + c, index + 1, uniqueWords);
	        }
	    }
}
