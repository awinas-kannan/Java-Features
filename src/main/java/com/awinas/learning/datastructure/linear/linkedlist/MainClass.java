package com.awinas.learning.datastructure.linear.linkedlist;

/**
 * @author gbs05349
 *
 */
public class MainClass {

	public static void main(String[] args) {

		LinkedList list = new LinkedList();
		list.add(20);
		list.add(30);
		list.add(22);
		list.add(44);
		list.add(54);

		list.addFirst(100);

		list.add(2, 10);
		list.add(2, 5);

		list.getAllData();
		System.out.println("********");
		list.remove(1);
		list.getAllData();
		System.out.println("********");

		list.remove(new Integer(100));
		list.getAllData();
		System.out.println("********");

		list.remove(new Integer(44));
		list.getAllData();
		System.out.println("********");

		list.remove(list.size() - 1);
		list.getAllData();
		System.out.println("********");
		
		System.out.println("size " + list.size);
		list.remove(new Integer(22));
		list.getAllData();
		System.out.println("********");
		
		list.remove(new Integer(100));
		list.getAllData();
		System.out.println("********");
		
		list.remove(3);
		list.getAllData();
		System.out.println("********");

	}
}
