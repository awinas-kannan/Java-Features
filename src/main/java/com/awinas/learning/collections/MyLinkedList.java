package com.awinas.learning.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

//https://howtodoinjava.com/java/collections/java-linkedlist-class/
//LinkedList should be preferred there are no large number of random access of element
//while there are a large number of add/remove operations.

//It does not implement RandomAccess interface. 
//So we can access elements in sequential order only. It does not support accessing elements randomly.

//Doubly linked list implementation which implements List and Deque interfaces.
//Therefore, It can also be used as a Queue, Deque or Stack.

//not synchronized

//Use Collections.synchronizedList(new LinkedList()) to get synchronized linkedlist.

//LinkedList Performance::
//manipulation is fast because no shifting needs to be occurred
//all add and remove method provide very good performance O(1).
//add(E element) method is of O(1).
//get(int index) and add(int index, E element) methods are of O(n).
//remove(int index) method is of O(n).
//Iterator.remove() is O(1).
//ListIterator.add(E element) is O(1).
public class MyLinkedList {

	public static void main(String[] args) {

		// Create linked list
		List<String> linkedList = new LinkedList<>();

		// Add elements
		linkedList.add("A");
		linkedList.add("B");
		linkedList.add("C");
		linkedList.add("D");

		System.out.println(linkedList);

		// Add elements at specified position
		linkedList.add(4, "A");
		linkedList.add(5, "A");
		
		

		System.out.println(linkedList);

		// Remove element
		linkedList.remove("A"); // removes A
		linkedList.remove(0); // removes B

		System.out.println(linkedList);

		// Iterate
		ListIterator<String> itrator = linkedList.listIterator();

		while (itrator.hasNext()) {
			System.out.print(itrator.next() + "-");
		}
		System.out.println();

		// 1. LinkedList to Array
		String array[] = new String[linkedList.size()];
		linkedList.toArray(array);

		System.out.println(Arrays.toString(array));

		// 2. Array to LinkedList
		LinkedList<String> linkedListNew = new LinkedList<>(Arrays.asList(array));

		System.out.println(linkedListNew);

		// Sort
		Collections.sort(linkedList);
		System.out.println(linkedList);

		Collections.reverse(linkedList);
		System.out.println(linkedList);

		// sync list
		// return (list instanceof RandomAccess ? new
		// SynchronizedRandomAccessList<>(list) :new SynchronizedList<>(list));

		// This do not implement random access // only seq access
		List<String> suncList = Collections.synchronizedList(linkedListNew);

	}
}
