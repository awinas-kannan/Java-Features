package com.awinas.learning.dp.structural.adapter;

class GFG_Test {

	public static void main(String args[]) {

		Bird sparrow = new Sparrow();
		System.out.println("Sparrow...");
		sparrow.fly();
		sparrow.makeSound();
		
		ToyDuck toyDuck = new PlasticToyDuck();
		System.out.println("ToyDuck...");
		toyDuck.squeak();

		// Wrap a bird in a birdAdapter so that it
		// behaves like toy duck
		// toy duck behaving like a bird
		
		//The client makes a request to the adapter by calling a method(squeak()) on it using the target interface(ToyDuck).
		ToyDuck birdAdapter = new BirdAdapter(sparrow);

		System.out.println("BirdAdapter...");
		//The adapter translates that request on the adaptee using the adaptee (Bird)interface.
		//Client receive the results of the call and is unaware of adapter�s presence.

		birdAdapter.squeak();
	}
}