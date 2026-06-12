package com.awinas.learning.dp.creational.singletondp;

public class EagerInitializedSingleton {

	private static final EagerInitializedSingleton instance = new EagerInitializedSingleton();

	// private constructor to avoid client applications to use constructor
	private EagerInitializedSingleton() {
		System.out.println("Creating EagerInitializedSingleton Instance");
	}

	public static EagerInitializedSingleton getInstance() {
		System.out.println("getting EagerInitializedSingleton");
		return instance;
	}
}