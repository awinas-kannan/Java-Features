package com.awinas.learning.datastructure.linear.linkedlistT;

public class LinkedList<T> {

	public LinkedList() {
		// constructor
	}

	Node<T> head;
	Node<T> tail;
	int size = 0;
	
	 

	public void add(T data) {
		Node<T> newNode = new Node<>();
		newNode.data = data;
		newNode.next = null;

		if (head == null) {
			head = newNode;
		} else {

			Node<T> s = head;

			while (s.next != null) {
				s = s.next;
			}
			s.next = newNode;

		}

		size++;
	}

	public void add(int index, T data) {
		checkPositionIndex(index);
		Node<T> newNode = new Node<>();
		newNode.data = data;

		if (index == 0) {
			addFirst(data);
			return;
		}

		Node<T> prev = head;
		Node<T> next = head.next;
		int init = 0;

		while (init < index - 1) {
			prev = next;
			next = next.next;
			init++;
		}
		prev.next = newNode;
		newNode.next = next;
		size++;

	}

	private void checkPositionIndex(int index) {
		System.out.println("index : " + index + " Size " + size);

		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

	}

	private void checkElementIndex(int index) {
		System.out.println("index : " + index + " Size " + size);

		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

	}

	public void addFirst(T data) {

		Node<T> newNode = new Node<>();
		newNode.data = data;
		if (head == null) {
			newNode.next = null;
		} else {
			newNode.next = head;
		}
		head = newNode;

		size++;

	}

	public T get(int index) {
		checkElementIndex(index);

		Node<T> prev = head;
		Node<T> next = head.next;
		int init = 0;

		while (init < index - 1) {
			prev = next;
			next = next.next;
			init++;
		}
		if (index == 0) {
			return prev.data;
		} else {
			return next.data;
		}

	}
	
	//size = 10
	//5
	//10 -5 = 5
	//Fast & SlowPointer
	
	///count 
	//icg //institional clients
	//basel // ccar
	
	
	public T getLast(int index) {
		index = size - index;
		checkElementIndex(index);
		return get(index);
	}
	

	public void remove(int index) {
		checkElementIndex(index);

		Node<T> prev = head;
		Node<T> next = head.next;
		int init = 0;

		while (init < index - 1) {
			prev = next;
			next = next.next;
			init++;
		}
		if (index == 0) {
			head = prev.next;
		}
		/*
		 * else if (index == 1) { // else conditon is similer to this head.next =
		 * next.next; }
		 */
		else {
			prev.next = next.next;
		}
		size--;

	}

	public void remove(T obj) {
		T input = obj;
		Node<T> prev = head;
		Node<T> next = head.next;

		if (input.equals(prev.data)) {
			head = prev.next;
			size--;
			return;
		}

		int init = 1;
		while (init < size - 1) {
			prev = next;
			next = next.next;
			if (input.equals(next.data)) {
				prev.next = next.next;
				size--;
				return;
			}

			init++;
		}

	}

	public void getAllData() {
		Node<T> n = head;
		while (n.next != null) {
			System.out.println("val = " + n.data);
			n = n.next;
		}
		System.out.println("val = " + n.data);

	}

	public int size() {
		return size;
	}
}
