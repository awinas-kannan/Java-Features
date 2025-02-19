package com.awinas.learning.solid.liskov.bad;

class Payment {
	void processPayment(double amount) {
		System.out.println("Processing payment of $" + amount);
	}

	void applyCashDiscount() {
		System.out.println("Applying cash discount...");
	}
}



