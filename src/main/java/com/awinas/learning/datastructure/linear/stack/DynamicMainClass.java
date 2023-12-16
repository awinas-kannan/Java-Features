package com.awinas.learning.datastructure.linear.stack;

public class DynamicMainClass {

	public static void main(String[] args) {
		DynamicStack stack = new DynamicStack();
		System.out.println(stack.isEmpty());
		// stack.peek();
		stack.push(5);
		stack.push(6);
		stack.push(8);
		stack.push(9);
		stack.push(10);

		System.out.println(stack.show());

		System.out.println(stack.peek());

		System.out.println(stack.show());

		System.out.println("*****Pop******");
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
		System.out.println(stack.show());

	}

}
