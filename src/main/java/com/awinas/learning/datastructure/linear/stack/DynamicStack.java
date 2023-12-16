package com.awinas.learning.datastructure.linear.stack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EmptyStackException;

public class DynamicStack {

	private Object[] elementData;
	int elementCount;

	public void push(Object o) {
		grow();
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

		elementData = Arrays.copyOf(elementData, elementCount - 1);
		// elementData[elementCount - 1] = null;
		elementCount--;

		return o;
	}

	public int size() {
		return elementCount;
	}

	public String show() {

		return Arrays.toString(elementData);

	}

	public boolean isEmpty() {
		return elementCount == 0;
	}

	private void grow() {
		// this if condition can be in constructor also
		if (elementCount == 0)
			elementData = new Object[0];

		elementData = Arrays.copyOf(elementData, elementCount + 1);

//		T[] oldData = elementData;
//		elementData = new T[elementCount + 1];
//		elementData = oldData;
	}

//	private void grow(Class<T> clasz) {
//		//this if condition can be in constructor also
//		if (elementCount == 0)
//			elementData = (T[]) Array.newInstance(clasz, 0);
//
//		elementData = Arrays.copyOf(elementData, elementCount + 1);
//
//	}
}
