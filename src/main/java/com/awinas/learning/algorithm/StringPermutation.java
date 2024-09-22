package com.awinas.learning.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringPermutation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        List<String> permutations = calculatePermutations(input);
        System.out.println("Permutations: " + permutations);
        scanner.close();
    }

    public static List<String> calculatePermutations(String str) {
        List<String> permutations = new ArrayList<>();

        if (str.length() == 0) {
            permutations.add("");
            return permutations;
        }

        char firstChar = str.charAt(0);
        String remainingString = str.substring(1);
        List<String> words = calculatePermutations(remainingString);

        for (String word : words) {
            for (int i = 0; i <= word.length(); i++) {
                String prefix = word.substring(0, i);
                String suffix = word.substring(i);
                permutations.add(prefix + firstChar + suffix);
            }
        }

        return permutations;
    }
}


// awi
// wai 
// wia 
// aiw 
// iaw 
// iwa
 
