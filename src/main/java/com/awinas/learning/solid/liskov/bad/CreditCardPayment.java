package com.awinas.learning.solid.liskov.bad;

class CreditCardPayment extends Payment {
	@Override
	void processPayment(double amount) {
		System.out.println("Processing credit card payment of $" + amount);
	}
}
