package com.awinas.learning.datastructure.linear.arraylist;

import java.util.Arrays;

public class Arraylist<T> implements List<T> {

	private Object[] elementData;
	int size;

	public Arraylist() {
		elementData = new Object[0];
	}

	@Override
	public void add(T data) {
		grow();
		elementData[size] = data;
		size++;
	}

	@Override
	public void add(int index, T data) {
		checkIndex(index);
		grow();
		for (int i = size - 1; i >= index; i--) {
			elementData[i + 1] = elementData[i];
		}
		elementData[index] = data;
		size++;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		checkIndex(index);
		return (T) elementData[index];
	}

	private void checkIndex(int index) {
		if (index > (size() - 1) || index < 0) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
	}

	@Override
	public int size() {
		return size;
	}

	private void grow() {
		elementData = Arrays.copyOf(elementData, size + 1);
	}

	@Override
	public String toString() {
		return "Arraylist [elementData=" + Arrays.toString(elementData) + "]";
	}

	@Override
	public void remove(int index) {
		checkIndex(index);

		for (int i = index; i < size() - 1; i++) {
			elementData[i] = elementData[i + 1];
		}
		elementData = Arrays.copyOf(elementData, size()-1);
		size--;
	}

}
