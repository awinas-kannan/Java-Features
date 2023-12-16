package com.awinas.learning.datastructure.linear.DoubleLinkedList;

public class DoubleLinkedList<T> implements List<T> {

	Node<T> head;
	int size;

	@Override
	public void add(T data) {
		Node<T> newNode = new Node<>();
		newNode.data = data;

		if (head == null) {
			addFirst(data);
			return;
		}

		Node<T> node = head;
		while (node.nextNode != null) {
			node = node.nextNode;
		}
		node.nextNode = newNode;
		newNode.prevNode = node;
		size++;

	}

	@Override
	public void add(int index, T data) {
		Node<T> newNode = new Node<>();
		newNode.data = data;

		if (head == null) {
			addFirst(data);
			return;
		}
		Node<T> node = head;
		for (int i = 0; i < index; i++) {
			node = node.nextNode;
		}
		node.prevNode.nextNode = newNode;
		newNode.prevNode = node.prevNode;
		newNode.nextNode = node;
		node.prevNode = newNode;
		size++;

	}

	@Override
	public T get(int index) {
		checkIndex(index);
		Node<T> node = head;
		for (int i = 0; i < index; i++) {
			node = node.nextNode;
		}

		return node.data;
	}

	private void checkIndex(int index) {
		if (!(index < size)) {
			throw new IndexOutOfBoundsException("invalid index");
		}
	}

	public void addFirst(T data) {
		Node<T> newNode = new Node<>();
		newNode.data = data;
		if (head == null) {
			head = newNode;
			size++;
			return;
		}
		newNode.nextNode = head;
		head.prevNode = newNode;
		head = newNode;
		size++;
	}

	public String toString() {
		System.out.println("printing List...");
		String str = "DoubleLinkedList [";
		Node<T> iterationNode = head;
		while (iterationNode != null) {
			str = str + iterationNode.data + ",";
			iterationNode = iterationNode.nextNode;
		}
		return str + "]";
	}

	@Override
	public int size() {

		return size;
	}

	private class Node<T> {

		T data;
		Node<T> prevNode;
		Node<T> nextNode;

	}
}
