package com.awinas.learning.coreconcepts.interfaceevolution.java15;

public class PaymentDemoJava15 {
	public static void main(String[] args) {
		Payment card = new CardPayment();
		card.processPayment(500);
	}
}
