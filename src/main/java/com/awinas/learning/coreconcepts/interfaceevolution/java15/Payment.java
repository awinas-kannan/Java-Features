package com.awinas.learning.coreconcepts.interfaceevolution.java15;

// Java 15 Feature: Sealed Interfaces
public sealed interface Payment permits CardPayment, UpiPayment, WalletPayment {
	void processPayment(double amount);
}

