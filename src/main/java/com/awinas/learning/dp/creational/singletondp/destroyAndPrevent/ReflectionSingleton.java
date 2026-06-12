package com.awinas.learning.dp.creational.singletondp.destroyAndPrevent;

public class ReflectionSingleton {

	private static final ReflectionSingleton instance = new ReflectionSingleton();

	// private constructor to avoid client applications to use constructor
	private ReflectionSingleton() {
		System.out.println("Creating EagerInitializedSingleton Instance");

//This is one way of avoid new instance creation throug reflection 		
//		if (instance != null) {
//			throw new RuntimeException();
//		}
	}

	public static ReflectionSingleton getInstance() {
		System.out.println("getting EagerInitializedSingleton");
		return instance;
	}
}