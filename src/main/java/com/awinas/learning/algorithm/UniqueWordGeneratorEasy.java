package com.awinas.learning.algorithm;

import java.util.HashSet;
import java.util.Set;

//The time complexity of the above program is O(N^10), where N is the number of valid characters. 
//This is because there are nested loops iterating over each valid character, resulting in a total of 10 nested loops. 
//Each loop has N iterations, so the overall time complexity is O(N^10).
////
//The space complexity of the program is O(N^10) as well. 
//This is because the program uses a HashSet to store unique words. 
//In the worst case scenario, all possible combinations of 10 characters will be unique, 
//resulting in N^10 unique words stored in the HashSet. Therefore, the space complexity is O(N^10).

public class UniqueWordGeneratorEasy {
    public static void main(String[] args) {
        // Define the valid characters
        char[] validChars = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();

        // Use a HashSet to store unique words
        Set<String> uniqueWords = new HashSet<>();

        // Nested loops to generate all possible combinations
        for (char c1 : validChars) {
            for (char c2 : validChars) {
                for (char c3 : validChars) {
                    for (char c4 : validChars) {
                        for (char c5 : validChars) {
                            for (char c6 : validChars) {
                                for (char c7 : validChars) {
                                    for (char c8 : validChars) {
                                        for (char c9 : validChars) {
                                            for (char c10 : validChars) {
                                                String word = "" + c1 + c2 + c3 + c4 + c5 + c6 + c7 + c8 + c9 + c10;
                                                uniqueWords.add(word);
                                                System.out.println(word);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Print all unique words
        for (String word : uniqueWords) {
            System.out.println(word);
        }
    }
}

