package com.awinas.learning.solid.liskov.bad;

class CashPayment extends Payment {
	@Override
	void processPayment(double amount) {
		System.out.println("Processing cash payment of $" + amount);
	}
}
