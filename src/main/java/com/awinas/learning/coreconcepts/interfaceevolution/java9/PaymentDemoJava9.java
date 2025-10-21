package com.awinas.learning.coreconcepts.interfaceevolution.java9;

public class PaymentDemoJava9 {
	public static void main(String[] args) {
		Payment payment = new WalletPayment();
		payment.processPayment(1200);
		payment.printReceipt(1200); // uses private helper internally
		Payment.printServiceInfo(); // static method
	}
}
