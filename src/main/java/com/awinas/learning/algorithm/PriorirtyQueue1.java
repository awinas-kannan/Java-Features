package com.awinas.learning.algorithm;

import java.util.PriorityQueue;


//1. Find the Kth Largest Element in an Array
//Problem: Given an array of integers, find the Kth largest element in the array.
//
//Input: An array of integers arr[] and an integer k.
//Output: Return the Kth largest element.
//
//Example:
//
//text
//Copy code
//Input: arr[] = [3, 2, 1, 5, 6, 4], k = 2
//Output: 5
//Explanation: The 2nd largest element is 5.
//Hint: You can use a min-heap (Priority Queue) with size k. Insert elements into the heap, and if the heap size exceeds k, remove the smallest element. The root of the heap will give you the Kth largest element.

public class PriorirtyQueue1 {
	 public static int findKthLargest(int[] arr, int k) {
	        if (k <= 0 || k > arr.length) {
	            return -1; // or any other value to indicate invalid input
	        }
	        
	        PriorityQueue<Integer> pq = new PriorityQueue<>();
	        for (int num : arr) {
	            pq.offer(num);
	            if (pq.size() > k) {
	                pq.poll();
	            }
	        }
	        
	        return pq.peek();
	    }

	    public static void main(String[] args) {
	        int[] arr = {3, 1, 5, 2, 4};
	        int k = 3;
	        int kthLargest = findKthLargest(arr, k);
	        System.out.println("The " + k + "th largest element in the array is: " + kthLargest);
	    }
}
