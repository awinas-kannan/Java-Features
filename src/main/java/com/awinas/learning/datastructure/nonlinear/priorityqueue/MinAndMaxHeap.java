package com.awinas.learning.datastructure.nonlinear.priorityqueue;

import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * 
 * 
 * We can’t create a PriorityQueue of Objects that are non-comparable 
 * 
 * 
Common Operations on PriorityQueue:
Insert an element: add() or offer() — Time complexity: O(log N).
Access the highest priority element (smallest in min-heap): peek() — Time complexity: O(1).
Remove the highest priority element: poll() — Time complexity: O(log N).

Conclusion:
PriorityQueue is a versatile data structure useful for scheduling, sorting by priority, and other applications where the order of processing depends on priority rather than the order of insertion.
You can use the default min-heap behavior or customize it to a max-heap by using comparators.
 */
public class MinAndMaxHeap {
	public static void main(String[] args) {
		minheap();

		maxheap();
	}

	private static void minheap() {
		// Create a min heap using PriorityQueue
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();

		// Insert elements into the min heap
		minHeap.add(20);
		minHeap.add(10);
		minHeap.add(30);
		minHeap.add(5);
		minHeap.add(1);
		minHeap.add(2);
		minHeap.add(3);
		minHeap.add(4);

		// Display the smallest element (root)
		System.out.println("minHeap " + minHeap.toString());
		System.out.println("Min element: " + minHeap.peek()); // Output: 5

		// Remove the smallest element and display the next smallest
		while (!minHeap.isEmpty()) {
			System.out.println("Polling: " + minHeap.poll());
		}
	}

	private static void maxheap() {
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

		// Insert elements into the max heap
		maxHeap.add(20);
		maxHeap.add(10);
		maxHeap.add(30);
		maxHeap.add(5);

		System.out.println("maxHeap " + maxHeap);
		// Display the largest element (root)
		System.out.println("Max element: " + maxHeap.peek()); // Output: 30

		// Remove the largest element and display the next largest
		while (!maxHeap.isEmpty()) {
			System.out.println("Polling: " + maxHeap.poll());
		}
	}
}
