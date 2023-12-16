package com.awinas.learning.datastructure.linear.DoubleLinkedList;

public interface List<T> {

	void add(T data);

	void add(int index, T data);

	T get(int index);

	public String toString();

	public int size();

}
