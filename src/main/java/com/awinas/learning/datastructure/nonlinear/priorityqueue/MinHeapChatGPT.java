package com.awinas.learning.datastructure.nonlinear.priorityqueue;

import java.util.Arrays;

//https://www.digitalocean.com/community/tutorials/min-heap-binary-tree
//https://www.digitalocean.com/community/tutorials/level-order-traversal-in-a-binary-tree
class MinHeap1 {
	private int[] heap;
	private int size;
	private int capacity;

	public MinHeap1(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.heap = new int[capacity];
	}

	private int parent(int i) {
		return (i - 1) / 2;
	}

	private int leftChild(int i) {
		return 2 * i + 1;
	}

	private int rightChild(int i) {
		return 2 * i + 2;
	}

	private void swap(int i, int j) {
		int temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}

	private void ensureCapacity() {
		if (size == capacity) {
			heap = Arrays.copyOf(heap, capacity * 2);
			capacity *= 2;
		}
	}

	// Insert a new value into the heap
	public void insert(int value) {
		ensureCapacity();
		heap[size] = value;
		size++;
		heapifyUp(size - 1);
	}

	// Heapify-up to maintain the heap property
	private void heapifyUp(int i) {
		while (i != 0 && heap[parent(i)] > heap[i]) {
			swap(i, parent(i));
			i = parent(i);
		}
	}

// 5 15 10 30 25 35 20 
// 20 15 10 30 25 35
// 10 15 20 30 25 35
// 
// 10	
// 35 15 20 30 25
// 
	
	// Remove and return the minimum element from the heap
	public int extractMin() {
		if (size == 0)
			throw new IllegalStateException("Heap is empty");
		int min = heap[0];
		heap[0] = heap[size - 1];
		size--;
		heapifyDown(0);
		return min;
	}

	// Heapify-down to maintain the heap property
	private void heapifyDown(int i) {
		int smallest = i;
		int left = leftChild(i);
		int right = rightChild(i);

		if (left < size && heap[left] < heap[smallest]) {
			smallest = left;
		}
		if (right < size && heap[right] < heap[smallest]) {
			smallest = right;
		}
		if (smallest != i) {
			swap(i, smallest);
			heapifyDown(smallest);
		}
	}

	// Peek at the minimum element without removing it
	public int peek() {
		if (size == 0)
			throw new IllegalStateException("Heap is empty");
		return heap[0];
	}

	// Print the heap
	public void printHeap() {
		for (int i = 0; i < size; i++) {
			System.out.print(heap[i] + " ");
		}
		System.out.println();
	}

	public int getSize() {
		return size;
	}
}

public class MinHeapChatGPT {
	public static void main(String[] args) {
		MinHeap1 minHeap = new MinHeap1(10);
		minHeap.insert(5);
		minHeap.insert(25);
		minHeap.insert(10);
		minHeap.insert(30);
		minHeap.insert(15);
		minHeap.insert(35);
		minHeap.insert(20);

		System.out.println("Min element: " + minHeap.peek()); // Output: 1
		minHeap.printHeap(); // Output: 1 5 30 10

		System.out.println("Extracting min: " + minHeap.extractMin()); // Output: 1
		minHeap.printHeap();

		System.out.println("Extracting min: " + minHeap.extractMin());
		minHeap.printHeap();

		System.out.println("Extracting min: " + minHeap.extractMin());
		minHeap.printHeap();

		System.out.println("Extracting min: " + minHeap.extractMin());
		minHeap.printHeap();

		System.out.println("Extracting min: " + minHeap.extractMin());
		minHeap.printHeap();

		System.out.println("Extracting min: " + minHeap.extractMin());
		minHeap.printHeap();

		System.out.println("Extracting min: " + minHeap.extractMin());
		minHeap.printHeap();

		System.out.println("Extracting min: " + minHeap.extractMin());
		minHeap.printHeap();
	}
}
