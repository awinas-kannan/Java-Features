package com.awinas.learning.coreconcepts.interfaceevolution.java9;

// Implementation
class WalletPayment implements Payment {
	@Override
	public void processPayment(double amount) {
		double total = amount + (amount * SERVICE_CHARGE);
		System.out.println("Wallet payment processed for $" + total);
	}

	@Override
	public String getPaymentType() {
		return "Wallet";
	}
}
