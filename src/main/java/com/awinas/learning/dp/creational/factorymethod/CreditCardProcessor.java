package com.awinas.learning.dp.creational.factorymethod;

public class CreditCardProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of ₹" + amount);
        System.out.println("Connecting to card gateway... Payment approved.");
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
}
