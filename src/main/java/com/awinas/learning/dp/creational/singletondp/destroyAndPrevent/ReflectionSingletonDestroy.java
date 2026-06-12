package com.awinas.learning.dp.creational.singletondp.destroyAndPrevent;

import java.lang.reflect.Constructor;

//Check logs
//Hash code varies after setting singleton class as accessible, 
//which shows new  instance is created each time .

public class ReflectionSingletonDestroy {

	public static void main(String[] args) {
		ReflectionSingleton instanceOne = ReflectionSingleton.getInstance();
		ReflectionSingleton instanceOne_1 = ReflectionSingleton.getInstance();
		ReflectionSingleton instanceTwo = null;
		ReflectionSingleton instanceTwo_1 = null;
		try {
			Constructor[] constructors = ReflectionSingleton.class.getDeclaredConstructors();
			for (Constructor constructor : constructors) {
				// Below code will destroy the singleton pattern
				System.out.println(constructor);
				constructor.setAccessible(true);
				System.out.println(constructor);
				instanceTwo = (ReflectionSingleton) constructor.newInstance();
				instanceTwo_1 = (ReflectionSingleton) constructor.newInstance();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(instanceOne.hashCode());
		System.out.println(instanceOne_1.hashCode());
		System.out.println(instanceTwo.hashCode());
		System.out.println(instanceTwo_1.hashCode());
	}

}