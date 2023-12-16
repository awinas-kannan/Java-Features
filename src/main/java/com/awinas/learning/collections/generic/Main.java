package com.awinas.learning.collections.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Remember PECS: "Producer Extends, Consumer Super".
//https://stackoverflow.com/questions/4343202/difference-between-super-t-and-extends-t-in-java
public class Main {

	public static void main(String[] args) {
		TextendNumber<Integer> obj1 = new TextendNumber<>();
		obj1.val = 10;
		obj1.val = new Integer(100);
		// obj1.val = new Double(10);
		// obj1.val = 10.1 ; not possible here
		obj1.type();

		TextendNumber<Number> obj2 = new TextendNumber<>();
		obj2.val = 10.1;
		obj2.type();
		obj2.val = new Long(10);
		obj2.type();

		// TextendNumber<String> obj = new TextendNumber<>();

		System.out.println("*****************************************");

		QSuperT<Integer> qobj = new QSuperT<>();
		qobj.qSuperT(new ArrayList<Integer>(Arrays.asList(1, 1)));
		qobj.qSuperT(new ArrayList<Number>(Arrays.asList(1, 2)));
		// qobj.qSuperT(new ArrayList<String>(Arrays.asList(1, 2)));

		List<? extends Integer> foo2 = new ArrayList<>();
		Integer a = foo2.get(1);
		// Double d = foo2.get(1);;
		// foo2.add(1);
		// No writing

		List<? extends Number> foo10 = new ArrayList<>();
		// Integer d = foo10.get(1);

		System.out.println("*****************************************");

		List<? super Number> foo3 = new ArrayList<>();
		foo3.add(1);
		foo3.add(1.1);
		foo3.add(111334556);

		foo3.get(1);
		// foo3.add("str");

		List<? super Integer> foo4 = new ArrayList<>();
		foo4.add(1);
		// foo4.add(1.1);
		foo4.add(111334556);
	}
}
