package com.awinas.learning.datastructure.linear.queue;

import java.util.Arrays;

public class DynamicQueue {

	Object[] queue;
	int elementCount;
	// int size = 0;

	public DynamicQueue() {
		this.queue = new Object[0];
	}

	public void enQueue(Object data) {
		grow();
		queue[elementCount] = data;
		elementCount++;
	}

	public Object peek() {
		if (elementCount != 0)
			return queue[0];
		return "queue is empty";
	}

	public Object deQueue() {
		if (size() != 0) {
			Object o = peek();
			int newSize = size() - 1;
			Object[] copyQueue = new Object[newSize];
			System.arraycopy(queue, 1, copyQueue, 0, newSize);
			queue = null;
			queue = copyQueue;
			copyQueue = null;
			elementCount--;
			return o;
		} else {
			return "queue is empty";
		}
	}

	private void grow() {

		queue = Arrays.copyOf(queue, elementCount + 1);

	}

	public int size() {
		return elementCount;
	}

	public String show() {
		return Arrays.toString(queue);
	}
}
