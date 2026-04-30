package com.awinas.learning.collections.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

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
		String [] array = new String[linkedList.size()];
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

		utilMethod();
	}

	public static void utilMethod() {
		System.out.println("\n========== LinkedList - Frequently Used Methods ==========\n");

		// --- 1. Creation & Initialization ---
		LinkedList<String> list = new LinkedList<>();
		LinkedList<String> fromCollection = new LinkedList<>(Arrays.asList("Apple", "Banana", "Cherry"));

		// --- 2. add() / addAll() ---
		list.add("Dog");
		list.add("Elephant");
		list.add("Fox");
		list.add("Dog");                 // duplicates allowed
		list.addAll(Arrays.asList("Goat", "Horse"));
		System.out.println("After add/addAll    : " + list);

		// --- 3. addFirst() / addLast() — O(1) LinkedList specific ---
		list.addFirst("Ant");
		list.addLast("Zebra");
		System.out.println("addFirst/addLast    : " + list);

		// --- 4. getFirst() / getLast() — O(1) ---
		System.out.println("getFirst()          : " + list.getFirst());
		System.out.println("getLast()           : " + list.getLast());

		// --- 5. get(index) — O(n), traverses from head or tail ---
		System.out.println("get(3)              : " + list.get(3));

		// --- 6. set() — replace element at index ---
		list.set(1, "Bear");
		System.out.println("After set(1, Bear)  : " + list);

		// --- 7. removeFirst() / removeLast() — O(1) ---
		list.removeFirst();
		list.removeLast();
		System.out.println("removeFirst/Last    : " + list);

		// --- 8. remove(Object) / remove(index) ---
		list.remove("Dog");             // removes first occurrence
		list.remove(0);                 // removes by index
		System.out.println("After remove        : " + list);

		// --- 9. contains() / indexOf() / lastIndexOf() ---
		System.out.println("contains Fox        : " + list.contains("Fox"));
		list.add("Fox");
		System.out.println("indexOf Fox         : " + list.indexOf("Fox"));
		System.out.println("lastIndexOf Fox     : " + list.lastIndexOf("Fox"));

		// --- 10. size() / isEmpty() ---
		System.out.println("size                : " + list.size());
		System.out.println("isEmpty             : " + list.isEmpty());

		// =============================================
		// Queue operations (FIFO) — LinkedList implements Queue
		// =============================================
		System.out.println("\n--- Queue Operations (FIFO) ---");
		LinkedList<String> queue = new LinkedList<>(Arrays.asList("First", "Second", "Third"));

		// offer() — adds to tail (same as add, but returns false instead of exception)
		queue.offer("Fourth");
		System.out.println("After offer         : " + queue);

		// peek() — view head without removing (returns null if empty)
		System.out.println("peek()              : " + queue.peek());

		// poll() — remove and return head (returns null if empty)
		System.out.println("poll()              : " + queue.poll());
		System.out.println("After poll          : " + queue);

		// element() — view head (throws NoSuchElementException if empty)
		System.out.println("element()           : " + queue.element());

		// =============================================
		// Deque operations (Double-Ended Queue)
		// =============================================
		System.out.println("\n--- Deque Operations ---");
		LinkedList<String> deque = new LinkedList<>(Arrays.asList("B", "C", "D"));

		deque.offerFirst("A");
		deque.offerLast("E");
		System.out.println("offerFirst/Last     : " + deque);

		System.out.println("peekFirst()         : " + deque.peekFirst());
		System.out.println("peekLast()          : " + deque.peekLast());

		System.out.println("pollFirst()         : " + deque.pollFirst());
		System.out.println("pollLast()          : " + deque.pollLast());
		System.out.println("After pollFirst/Last: " + deque);

		// =============================================
		// Stack operations (LIFO) — push/pop
		// =============================================
		System.out.println("\n--- Stack Operations (LIFO) ---");
		LinkedList<String> stack = new LinkedList<>();
		stack.push("Bottom");
		stack.push("Middle");
		stack.push("Top");
		System.out.println("After push          : " + stack);
		System.out.println("pop()               : " + stack.pop());
		System.out.println("After pop           : " + stack);
		System.out.println("peek() (stack top)  : " + stack.peek());

		// --- 11. Iteration ---
		System.out.println("\n--- Iteration ---");
		LinkedList<String> items = new LinkedList<>(Arrays.asList("A", "B", "C", "D", "E"));

		System.out.print("for-each loop       : ");
		for (String s : items) {
			System.out.print(s + " ");
		}
		System.out.println();

		System.out.print("Iterator            : ");
		Iterator<String> it = items.iterator();
		while (it.hasNext()) {
			System.out.print(it.next() + " ");
		}
		System.out.println();

		System.out.print("DescendingIterator  : ");
		Iterator<String> descIt = items.descendingIterator();
		while (descIt.hasNext()) {
			System.out.print(descIt.next() + " ");
		}
		System.out.println();

		System.out.print("ListIterator(rev)   : ");
		ListIterator<String> lit = items.listIterator(items.size());
		while (lit.hasPrevious()) {
			System.out.print(lit.previous() + " ");
		}
		System.out.println();

		System.out.print("forEach (lambda)    : ");
		items.forEach(s -> System.out.print(s + " "));
		System.out.println();

		// --- 12. sort() ---
		LinkedList<String> sortable = new LinkedList<>(Arrays.asList("Cherry", "Apple", "Banana"));
		sortable.sort(Comparator.naturalOrder());
		System.out.println("Sorted (natural)    : " + sortable);
		sortable.sort(Comparator.reverseOrder());
		System.out.println("Sorted (reverse)    : " + sortable);

		// --- 13. Stream operations ---
		List<String> filtered = items.stream()
				.filter(s -> s.compareTo("C") >= 0)
				.collect(Collectors.toList());
		System.out.println("Stream filter(>=C)  : " + filtered);

		// --- 14. removeIf() ---
		LinkedList<String> copy = new LinkedList<>(items);
		copy.removeIf(s -> s.equals("A") || s.equals("E"));
		System.out.println("removeIf(A or E)    : " + copy);

		// --- 15. toArray() ---
		String[] arr = items.toArray(new String[0]);
		System.out.println("toArray             : " + Arrays.toString(arr));

		// --- 16. subList() ---
		List<String> sub = items.subList(1, 4);
		System.out.println("subList(1,4)        : " + sub);

		// --- 17. clear() ---
		LinkedList<String> temp = new LinkedList<>(items);
		temp.clear();
		System.out.println("After clear         : " + temp + ", isEmpty: " + temp.isEmpty());

		// --- 18. Collections utility ---
		System.out.println("frequency(A)        : " + Collections.frequency(items, "A"));
		System.out.println("min                 : " + Collections.min(items));
		System.out.println("max                 : " + Collections.max(items));

		// --- 19. Synchronized wrapper ---
		List<String> syncList = Collections.synchronizedList(new LinkedList<>(items));
		System.out.println("Synchronized        : " + syncList);

		System.out.println("\n========== End of LinkedList utilMethod ==========\n");
	}
}
