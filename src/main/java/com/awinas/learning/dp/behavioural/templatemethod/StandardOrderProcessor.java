package com.awinas.learning.dp.behavioural.templatemethod;

// Standard order — regular pricing, no discount, standard payment
public class StandardOrderProcessor extends OrderProcessor {

    private static final double DELIVERY_CHARGE = 49.0;

    @Override
    protected double calculatePrice(double basePrice) {
        double total = basePrice + DELIVERY_CHARGE;
        System.out.println("[Price]     Base: Rs." + basePrice + " + Delivery: Rs." + DELIVERY_CHARGE + " = Rs." + total);
        return total;
    }

    // No discount override — uses default (no discount) from base class

    @Override
    protected void processPayment(String orderId, double amount) {
        System.out.println("[Payment]   Standard payment of Rs." + String.format("%.2f", amount) + " processed for " + orderId);
    }
}
