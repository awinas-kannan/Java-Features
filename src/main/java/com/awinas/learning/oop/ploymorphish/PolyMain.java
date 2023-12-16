package com.awinas.learning.oop.ploymorphish;

public class PolyMain {

	public static void main(String[] args) {
		ParentClass cc = new ChildClass();
		Integer i1 = 10;
		// Integer is the child of Number
		// So it can be set as input to method as input param as Number
		// If the child / parent class has method with same name and Integer Datatype
		// then that will be called
		// getNumber(Integer i1);
		System.out.println(cc.getNumber(i1));

		System.out.println(cc.getNumber(1, 2));
	}

}
