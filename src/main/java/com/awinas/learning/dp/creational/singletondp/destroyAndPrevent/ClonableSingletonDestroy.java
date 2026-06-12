package com.awinas.learning.dp.creational.singletondp.destroyAndPrevent;

import java.io.FileNotFoundException;
import java.io.IOException;

//https://dzone.com/articles/prevent-breaking-a-singleton-class-pattern

public class ClonableSingletonDestroy {

	public static void main(String[] args)
			throws FileNotFoundException, IOException, ClassNotFoundException, CloneNotSupportedException {

		ClonableSingleton instanceOne = ClonableSingleton.getInstance();
		System.out.println("hashCode of instance 1 is - " + instanceOne.hashCode());
		System.out.println("*********************************************************");
		ClonableSingleton instanceTwo = (ClonableSingleton) instanceOne.clone();

		System.out.println("hashCode of instance 2 is - " + instanceTwo.hashCode());
	}
}
