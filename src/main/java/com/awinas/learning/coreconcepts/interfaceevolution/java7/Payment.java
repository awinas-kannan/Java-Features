package com.awinas.learning.coreconcepts.interfaceevolution.java7;

// Java 7 Style Interface
public interface Payment {
	// Constants (implicitly public, static, final)
	double SERVICE_CHARGE = 0.02;

	// Abstract methods (implicitly public, abstract)
	void processPayment(double amount);

	String getPaymentType();
}
