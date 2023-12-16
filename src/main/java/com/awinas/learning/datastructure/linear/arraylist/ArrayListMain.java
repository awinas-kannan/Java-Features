package com.awinas.learning.datastructure.linear.arraylist;

public class ArrayListMain {

	public static void main(String[] args) {

		System.out.println("Adding few to list");
		List<Integer> list = new Arraylist<Integer>() {
			{
				add(4);
				add(6);
				add(5);
				add(1);
				add(6);

			}

		};

		System.out.println(list.toString());
		System.out.println("Size : " + list.size());
		list.add(2, 10);
		list.add(2, 11);
		System.out.println(list.toString());
		System.out.println("Size : " + list.size());

		System.out.println("Adding by index");
		list.add(0, 100);
		System.out.println(list.toString());
		System.out.println("Size : " + list.size());

		System.out.println("**********Remove****************");
		list.remove(0);
		list.remove(list.size()-1);
		System.out.println(list.toString());
		System.out.println("Size : " + list.size());
		list.remove(list.size());

	}
}
