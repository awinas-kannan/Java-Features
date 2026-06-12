package com.awinas.learning.dp.creational.singletondp.destroyAndPrevent;

import java.io.Serializable;

//https://dzone.com/articles/prevent-breaking-a-singleton-class-pattern

public class SerilizedSingleton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2482873388323471387L;
	private static SerilizedSingleton instance;

	private SerilizedSingleton() {
		System.out.println("Creating ThreadSafeSingletonUsingDoubleLocking Instance");
	}

	public static SerilizedSingleton getInstance() {
		System.out.println("getting ThreadSafeSingletonUsingDoubleLocking Instance");
		if (instance == null) {
			System.out.println(" ThreadSafeSingletonUsingDoubleLocking Instance = null ");
			synchronized (SerilizedSingleton.class) {
				System.out.println("Checking for null very first time");
				if (instance == null) {
					instance = new SerilizedSingleton();
				}
			}
		}
		return instance;
	}

	// Add this below code to avoid creation of new instance
	// while deserilization

//	protected Object readResolve() { 
//        return instance; 
//   }  
}
