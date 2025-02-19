package com.awinas.learning.solid.liskov.good;

public class Main {
	public static void main(String[] args) {
		Payment payment1 = new CreditCardPayment();
		payment1.processPayment(100); // ✅ Works fine

		Payment payment2 = new UPIPayment();
		payment2.processPayment(200); // ✅ Works fine

		CashPayment cashPayment = new CashPayment();
		cashPayment.processPayment(300); // ✅ Works fine
		cashPayment.applyCashDiscount(); // ✅ Works fine
	}
}