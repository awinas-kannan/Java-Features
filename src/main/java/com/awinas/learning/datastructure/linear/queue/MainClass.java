package com.awinas.learning.datastructure.linear.queue;

import java.util.LinkedList;

public class MainClass {
	public static void main(String[] args) {

		// Queue q= new PriorityQueue<>();
		//Queue q1= new LinkedList()<>();

		DynamicQueue queue = new DynamicQueue();
		System.out.println("size - " + queue.size());

		queue.enQueue(1);
		queue.enQueue(2);
		queue.enQueue(3);

		System.out.println(queue.show());

		System.out.println("peek " + queue.peek());
		System.out.println("de queue " + queue.deQueue());
		System.out.println(queue.show());

		queue.enQueue(4);
		System.out.println(queue.show());
		System.out.println("size - " + queue.size());

		System.out.println("de queue " + queue.deQueue());
		System.out.println("de queue " + queue.deQueue());
		System.out.println("de queue " + queue.deQueue());

		System.out.println("size - " + queue.size());
		System.out.println(queue.show());

		System.out.println("peek " + queue.peek());
		System.out.println("de queue " + queue.deQueue());
		System.out.println(queue.show());
		queue.enQueue(5);
		System.out.println(queue.show());
	}
}
