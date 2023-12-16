package com.awinas.learning.collections.generic;

public class TextendNumber<T extends Number> {

	public T val;

	public void type() {
		System.out.println(val.getClass().getName());
	}
}
