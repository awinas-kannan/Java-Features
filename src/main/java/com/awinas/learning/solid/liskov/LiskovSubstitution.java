package com.awinas.learning.solid.liskov;

public class LiskovSubstitution {

	public static void main(String[] args) {

		// Super class object replaced by sub class object
		Vehicle car = new Car();
		Vehicle bike = new Bike();
		System.out.println(car.getSpeed());
		System.out.println(bike.getSpeed());
	}

}

abstract class Vehicle {

	abstract int getSpeed();
}

class Car extends Vehicle {

	@Override
	int getSpeed() {
		return 100;

	}

}

class Bike extends Vehicle {

	@Override
	int getSpeed() {
		return 50;

	}

}
