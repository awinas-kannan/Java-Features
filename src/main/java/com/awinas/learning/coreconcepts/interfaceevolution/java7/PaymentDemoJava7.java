package com.awinas.learning.coreconcepts.interfaceevolution.java7;

public class PaymentDemoJava7 {
	public static void main(String[] args) {
		Payment payment = new CardPayment();
		payment.processPayment(1000);
		System.out.println("Type: " + payment.getPaymentType());
	}
}
