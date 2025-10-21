package com.awinas.learning.coreconcepts.interfaceevolution.java9;

// Java 9 Interface
public interface Payment {
	double SERVICE_CHARGE = 0.02;

	void processPayment(double amount);

	String getPaymentType();

	// ✅ Private helper method
	private void logTransaction(double amount) {
		System.out.println("[LOG] Amount $" + amount + " processed via " + getPaymentType());
	}

	// ✅ Default method using private helper
	default void printReceipt(double amount) {
		logTransaction(amount);
		System.out.println("Receipt: $" + amount + " via " + getPaymentType());
	}

	static void printServiceInfo() {
		System.out.println("Service charge: " + (SERVICE_CHARGE * 100) + "%");
	}
}
