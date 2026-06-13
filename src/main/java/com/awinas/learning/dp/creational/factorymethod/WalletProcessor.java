package com.awinas.learning.dp.creational.factorymethod;

public class WalletProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing wallet payment of ₹" + amount);
        System.out.println("Deducting from wallet balance... Payment approved.");
    }

    @Override
    public String getPaymentMethod() {
        return "Wallet";
    }
}
