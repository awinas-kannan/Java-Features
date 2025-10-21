package com.awinas.learning.coreconcepts.interfaceevolution.java8;

// Java 8 Interface
public interface Payment {
	double SERVICE_CHARGE = 0.02;

	void processPayment(double amount);

	String getPaymentType();

	// ✅ Default method (has body)
	default void printReceipt(double amount) {
		System.out.println("Receipt: Paid $" + amount + " via " + getPaymentType());
	}

	// ✅ Static method (utility logic)
	static void printServiceInfo() {
		System.out.println("Standard Service Charge: " + (SERVICE_CHARGE * 100) + "%");
	}
}
