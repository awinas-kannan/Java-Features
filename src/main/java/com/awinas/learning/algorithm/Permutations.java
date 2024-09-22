package com.awinas.learning.algorithm;

import java.util.ArrayList;
import java.util.List;

//n! = n * (n-1) * (n-2) * ... * 3 * 2 * 1.

public class Permutations {

	  public static List<String> getPermutations(String str) {
	        List<String> permutations = new ArrayList<>();
	        if (str == null || str.length() == 0) {
	            return permutations;
	        }
	        permute(str.toCharArray(), 0, permutations);
	        return permutations;
	    }

	    private static void permute(char[] strArray, int index, List<String> permutations) {
	        if (index == strArray.length - 1) {
	            permutations.add(String.valueOf(strArray));
	        } else {
	            for (int i = index; i < strArray.length; i++) {
	                swap(strArray, index, i);
	                permute(strArray, index + 1, permutations);
	                swap(strArray, index, i);
	            }
	        }
	    }

	    private static void swap(char[] strArray, int i, int j) {
	        char temp = strArray[i];
	        strArray[i] = strArray[j];
	        strArray[j] = temp;
	    }

	    public static void main(String[] args) {
	        String str = "ABCD";
	        List<String> permutations = getPermutations(str);
	        System.out.println("Permutations of " + str + ":");
	        for (String permutation : permutations) {
	            System.out.println(permutation);
	        }
	    }
}
