package com.awinas.learning.dp.creational.singletondp;

public class LazyInitializedSingleton {

	private static LazyInitializedSingleton instance;

	private LazyInitializedSingleton() {
		System.out.println("Creating LazyInitializedSingleton Instance");
	}

	public static LazyInitializedSingleton getInstance() {
		System.out.println("getting LazyInitializedSingleton");
		if (instance == null) {
			instance = new LazyInitializedSingleton();
		}
		return instance;
	}
}