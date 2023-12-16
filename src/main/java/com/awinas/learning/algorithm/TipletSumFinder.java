package com.awinas.learning.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//https://www.geeksforgeeks.org/find-a-triplet-that-sum-to-a-given-value/

//Time complexity: O(N^2). 
public class TipletSumFinder {

	public static void main(String[] args) {
		Integer A[] = { 1, 4, 45, 6, 10, 8 };
		// 1 4 6 8 10 45
		int sum = 22;
		int arr_size = A.length;

		TipletSumFinder finder = new TipletSumFinder();
		List<Integer> list = finder.findTripletSum(A, arr_size, sum);
		System.out.println(list);
	}

	public List<Integer> findTripletSum(Integer[] arr, int arrSize, int noToFind) {
		List<Integer> numberList = new ArrayList<>();
		Arrays.sort(arr);
		int l;
		int r;
		for (int i = 0; i < arrSize - 2; i++) {
			l = i + 1;
			r = arrSize - 1;
			while (l < r) {
				if (arr[i] + arr[l] + arr[r] == noToFind) {
					numberList.add(arr[i]);
					numberList.add(arr[l]);
					numberList.add(arr[r]);
					return numberList;
				} else if (arr[i] + arr[l] + arr[r] > noToFind) {
					// below statement
					// because obviously , if including r has resulted in maximum number
					// instead of decrementing r if we increment L
					// it will be higher value only
					// so only we are decrementing r
					r = r - 1;
				} else {
					l = l + 1;
				}
			}

		}
		return null;

	}

}
