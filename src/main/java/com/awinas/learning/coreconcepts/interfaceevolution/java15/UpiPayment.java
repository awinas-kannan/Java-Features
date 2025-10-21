package com.awinas.learning.coreconcepts.interfaceevolution.java15;

final class UpiPayment implements Payment {
	public void processPayment(double amount) {
		System.out.println("UPI payment processed for $" + amount);
	}
}