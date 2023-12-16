package com.awinas.learning.datastructure.linear.arraylist;

public interface List<T> {

	void add(T data);

	void add(int index, T data);

	T get(int index);

	void remove(int index);

	public String toString();

	public int size();

}
