package com.awinas.learning.coreconcepts.interfaceevolution.java8;

// Implementation
class UpiPayment implements Payment {
	@Override
	public void processPayment(double amount) {
		double total = amount + (amount * SERVICE_CHARGE);
		System.out.println("Processing UPI Payment: $" + total);
	}

	@Override
	public String getPaymentType() {
		return "UPI";
	}
}