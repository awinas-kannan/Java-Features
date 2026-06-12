package com.awinas.learning.dp.creational.singletondp;

public class StaticBlockSingleton {

	private static StaticBlockSingleton instance;

	private StaticBlockSingleton() {
		System.out.println("Creating StaticBlockSingleton Instance");
	}

	// static block initialization for exception handling
	static {
		try {
			instance = new StaticBlockSingleton();
		} catch (Exception e) {
			throw new RuntimeException("Exception occured in creating singleton instance");
		}
	}

	public static StaticBlockSingleton getInstance() {
		System.out.println("getting StaticBlockSingleton");
		return instance;
	}
}