package com.awinas.learning.coreconcepts.interfaceevolution.java15;

// Java 15 Feature: Sealed Interfaces
public sealed interface Payment permits CardPayment, UpiPayment {
	void processPayment(double amount);
}

