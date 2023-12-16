package com.awinas.learning.datastructure.linear.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {

	private Object[] elementData = new Object[10];
	int elementCount;
	int length = elementData.length;

	public void push(Object o) {
		// grow();
		elementData[elementCount] = o;
		elementCount++;
	}

	public Object peek() {
		if (elementCount == 0)
			throw new EmptyStackException();

		return elementData[elementCount - 1];

	}

	public Object pop() {
		Object o = peek();

		/*
		 * elementData = Arrays.copyOf(elementData, elementCount - 1); elementData =
		 * Arrays.copyOf(elementData, 10);
		 */
		elementData[elementCount - 1] = null;
		elementCount--;

		return o;
	}

	public int size() {
		return elementCount;
	}

	public String show() {
		/*
		 * StringBuilder sb = new StringBuilder(); sb.append("["); for (int i = 0; i <
		 * elementData.length ; i++) { sb.append(elementData[i]).append(","); }
		 * sb.append("]");
		 */
		return Arrays.toString(elementData);

	}

	public boolean isEmpty() {
		return elementCount == 0;

	}

	/*
	 * private void grow() {
	 * 
	 * elementData = Arrays.copyOf(elementData, elementCount + 1);
	 * 
	 * }
	 */

}
