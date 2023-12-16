package com.awinas.learning.innerclass;

//Non Static nested Classes are known as Inner Class
//Note that inner classes can access outer class private members 
//and at the same time we can hide inner class from outer world.
public class NestedInnerClass {

	public static void main(String[] args) {
		OuterClass oc = new OuterClass();
		oc.display_Inner();

		// make inner class public
		// OuterClass.InnerClass in = oc.new InnerClass(); //
		// InnerClass in = oc.new InnerClass(); //import required
		// System.out.println(in.print());
	}
}

class OuterClass {

	private int num = 1;

	private class InnerClass {
		// int num=2;
		
		public int print() {
			System.out.println("This is an inner class");
			return num;
		}
	}

	public void display_Inner() {

		InnerClass inner = new InnerClass();
		System.out.println(inner.print());

	}
}
