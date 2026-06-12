package com.awinas.learning.dp.creational.singletondp.destroyAndPrevent;

public class ClonableSingleton implements Cloneable {

	private static ClonableSingleton instance;

	private ClonableSingleton() {
		System.out.println("Creating ThreadSafeSingletonUsingDoubleLocking Instance");
	}

	public static ClonableSingleton getInstance() {
		System.out.println("getting ThreadSafeSingletonUsingDoubleLocking Instance");
		if (instance == null) {
			System.out.println(" ThreadSafeSingletonUsingDoubleLocking Instance = null ");
			synchronized (ClonableSingleton.class) {
				System.out.println("Checking for null very first time");
				if (instance == null) {
					instance = new ClonableSingleton();
				}
			}
		}
		return instance;
	}

//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		return super.clone();
//	}

	// Uncomment this line to avoid
	// cloning of singleton

	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		 throw new CloneNotSupportedException();
	}
}
