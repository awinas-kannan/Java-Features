package com.awinas.learning.dp.creational.factorymethod;

public class CashOnDeliveryProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Cash on Delivery selected for ₹" + amount);
        System.out.println("Payment will be collected at delivery.");
    }

    @Override
    public String getPaymentMethod() {
        return "Cash on Delivery";
    }
}
