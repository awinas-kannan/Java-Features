package com.awinas.learning;

public class LinkListMain {

	public static void main(String[] args) {

		MyLinkedList<Integer> list = new MyLinkedList<>();
		list.add(3);
		list.add(4);
		list.add(2);
		list.add(5);
		list.add(8);

		System.out.println(list.toString());

	}

}
