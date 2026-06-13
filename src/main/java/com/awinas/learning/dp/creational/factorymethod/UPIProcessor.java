package com.awinas.learning.dp.creational.factorymethod;

public class UPIProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing UPI payment of ₹" + amount);
        System.out.println("Sending UPI collect request... Payment approved.");
    }

    @Override
    public String getPaymentMethod() {
        return "UPI";
    }
}
