package com.awinas.learning.datastructure.linear.DoubleLinkedList;

import java.util.LinkedList;

public class MainClass {

	public static void main(String[] args) {
		System.out.println("Hi");
		java.util.List<String> utilList = new LinkedList<>();
		List<String> mylist = new DoubleLinkedList<>();
		mylist.add("awi");
		mylist.add("nas");
		mylist.add("kan");
		mylist.add("nan");
		System.out.println(mylist.toString());
		System.out.println(mylist.size());
		System.out.println(mylist.get(0));
		System.out.println(mylist.get(2));
		System.out.println(mylist.get(3));
		// System.out.println(mylist.get(5));
		mylist.add(2, "2");
		System.out.println(mylist.toString());
		System.out.println(mylist.size());

	}

}
