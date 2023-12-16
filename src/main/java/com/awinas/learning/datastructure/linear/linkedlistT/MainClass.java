package com.awinas.learning.datastructure.linear.linkedlistT;

/**
 * @author gbs05349
 *
 */
public class MainClass {

	public static void main(String[] args) {

		LinkedList<Integer> newList = new LinkedList<>();
		
		
		LinkedList<String> list = new LinkedList<>();

		list.add(0, "awinas");
		list.add(1, "awin");
		list.add(2, "awi");
		LinkedList<String> list2 = new LinkedList<>();

		list2.add(0, "awinas1");
		list2.add(1, "awin1");
		list2.add(2, "awi1");

		list.getAllData();

		Adress a = new Adress();
		Adress a1 = new Adress();
		Adress a2 = new Adress();
		a.setCity("madurai");
		a1.setCity("chennai");
		a2.setCity("salem");
		a.setState("TN");
		a1.setState("TN");
		a2.setState("TN");
		LinkedList<Adress> list1 = new LinkedList<>();
		list1.add(a);
		list1.add(a1);
		list1.add(1, a2);
		list1.getAllData();
		list1.remove(a);
		list1.getAllData();
		list1.remove(1);
		list1.getAllData();

		System.out.println("*** *** ***");

		@SuppressWarnings("rawtypes")
		LinkedList<LinkedList> listOfList = new LinkedList<>();
		listOfList.add(list);
		listOfList.add(list1);
		Adress a3 = (Adress) listOfList.get(1).get(0);
		System.out.println(a3.getState());

		System.out.println("*** *** ***");
		LinkedList<LinkedList<String>> lists = new LinkedList<>();
		lists.add(list);
		lists.add(list2);

		String str = lists.get(1).get(2);
		System.out.println(str);

	}
}
