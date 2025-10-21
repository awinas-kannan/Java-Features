package com.awinas.learning.coreconcepts.interfaceevolution.java7;

// Implementation class
public class CardPayment implements Payment {
    @Override
    public void processPayment(double amount) {
        double total = amount + (amount * SERVICE_CHARGE);
        System.out.println("Processing Card Payment: $" + total);
    }

    @Override
    public String getPaymentType() {
        return "Card";
    }
}
