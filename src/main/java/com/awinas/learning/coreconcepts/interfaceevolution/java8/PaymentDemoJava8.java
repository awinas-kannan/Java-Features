package com.awinas.learning.coreconcepts.interfaceevolution.java8;

public class PaymentDemoJava8 {
	public static void main(String[] args) {
		Payment payment = new UpiPayment();
		payment.processPayment(800);
		payment.printReceipt(800); // default method call
		Payment.printServiceInfo(); // static method call
	}
}
