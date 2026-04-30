package com.awinas.learning.coreconcepts.interfaceevolution.java15;

/*
 * The type WalletPayment that implements a sealed interface Payment should be a permitted subtype of Payment
 */
// Permitted subtype of sealed interface Payment — must be declared final, sealed, or non-sealed
final class WalletPayment implements Payment {

	@Override
	public void processPayment(double amount) {
		System.out.println("Processing Wallet payment of: " + amount);
	}
}