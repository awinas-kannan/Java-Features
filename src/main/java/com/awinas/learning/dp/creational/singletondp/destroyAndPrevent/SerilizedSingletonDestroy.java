package com.awinas.learning.dp.creational.singletondp.destroyAndPrevent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

//https://dzone.com/articles/prevent-breaking-a-singleton-class-pattern

public class SerilizedSingletonDestroy {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		SerilizedSingleton instanceOne = SerilizedSingleton.getInstance();
		
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream("file.text"));
		out.writeObject(instanceOne);
		out.close();
		
		System.out.println("*********************************************************");

		ObjectInput in = new ObjectInputStream(new FileInputStream("file.text"));
		SerilizedSingleton instanceTwo = (SerilizedSingleton) in.readObject();
		in.close();

		System.out.println("hashCode of instance 1 is - " + instanceOne.hashCode());
		System.out.println("hashCode of instance 2 is - " + instanceTwo.hashCode());
	}
}
