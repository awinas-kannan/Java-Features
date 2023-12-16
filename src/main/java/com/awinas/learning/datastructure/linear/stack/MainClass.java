package com.awinas.learning.datastructure.linear.stack;

public class MainClass {

	public static void main(String[] args) {

		/*
		 * Stack<Integer> stack = new Stack<>(); stack.add(1); stack.add(1);
		 * stack.add(1); stack.addElement(5); System.out.println(stack);
		 */

		Stack stack = new Stack();
		System.out.println(stack.isEmpty());
		stack.push(5);
		stack.push(6);
		stack.push(8);
		stack.push(9);
		stack.push(10);
		System.out.println(stack.show());

		System.out.println(stack.peek());

		System.out.println(stack.show());

		System.out.println(stack.pop());
		System.out.println(stack.peek());
		System.out.println(stack.show());

		stack.push(11);
		System.out.println(stack.show());
		System.out.println(stack.size());
		System.out.println(stack.peek());
		System.out.println(stack.pop());
		System.out.println(stack.peek());
		System.out.println(stack.isEmpty());
		/**/
	}

}
