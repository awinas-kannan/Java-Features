package com.awinas.learning.coreconcepts.interfaceevolution.java15;

// Only these classes can implement Payment
final class CardPayment implements Payment {
	public void processPayment(double amount) {
		System.out.println("Card payment processed for $" + amount);
	}
}