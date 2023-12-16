package com.awinas.learning;

public class MyLinkedList<T> {

	Node<T> root;
	int size = 0;

	public MyLinkedList() {
		root = null;
	}

	public void add(T input) {

		Node<T> node = new Node<>(input);
		if (root == null) {
			root = node;
			size++;
			return;
		}
		Node<T> n = root;
		while (n.next != null) {
			n = n.next;
		}

		n.next = node;
		size++;
	}

	public int lenght() {
		return size;
	}

	@Override
	public String toString() {
		String values = "";

		if (root == null) {
			return null;
		}
		Node<T> node = root;

		values = values + node.value + "  ";
		while (node.next != null) {
			values = values + node.next.value + " ";
			node = node.next;
		}

		return values;
	}

}

class Node<T> {

	T value;
	Node<T> next;

	Node(T val) {
		this.value = val;
		next = null;
	}
}