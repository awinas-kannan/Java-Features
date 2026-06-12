package com.awinas.learning.dp.creational.singletondp;

public class ThreadSafeSingleton {

	private static ThreadSafeSingleton instance;

	private ThreadSafeSingleton() {
		System.out.println("Creating ThreadSafeSingleton Instance");
	}

	public static synchronized ThreadSafeSingleton getInstance() {
		System.out.println("getting ThreadSafeSingleton Instance");
		if (instance == null) {
			instance = new ThreadSafeSingleton();
		}
		return instance;
	}

}