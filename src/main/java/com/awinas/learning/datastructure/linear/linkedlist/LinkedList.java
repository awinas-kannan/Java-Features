package com.awinas.learning.datastructure.linear.linkedlist;

public class LinkedList {

	public LinkedList() {
		// TODO Auto-generated constructor stub
	}

	Node head;
	Node tail;
	int size = 0;

	public void add(int data) {
		Node newNode = new Node();
		newNode.data = data;
		newNode.next = null;

		if (head == null) {
			head = newNode;
		} else {

			Node s = head;

			while (s.next != null) {
				s = s.next;
			}
			s.next = newNode;

		}

		size++;
	}

	public void add(int index, int data) {
		checkPositionIndex(index);
		Node newNode = new Node();
		newNode.data = data;

		if (index == 0) {
			addFirst(data);
			return;
		}

		Node prev = head;
		Node next = head.next;
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

	public void addFirst(int data) {

		Node newNode = new Node();
		newNode.data = data;
		if (head == null) {
			newNode.next = null;
		} else {
			newNode.next = head;
		}
		head = newNode;

		size++;

	}

	public void remove(int index) {
		checkElementIndex(index);

		Node prev = head;
		Node next = head.next;
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

	public void remove(Object obj) {
		Integer input = (Integer) obj;
		Node prev = head;
		Node next = head.next;

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
		Node n = head;
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
