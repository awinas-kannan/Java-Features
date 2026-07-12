package com.awinas.learning.dp.behavioural.templatemethod;

// Premium/Prime order — free delivery, loyalty discount, priority payment
public class PremiumOrderProcessor extends OrderProcessor {

    private static final double LOYALTY_DISCOUNT_PERCENT = 10.0;

    @Override
    protected double calculatePrice(double basePrice) {
        // Free delivery for premium members
        System.out.println("[Price]     Base: Rs." + basePrice + " + Delivery: FREE (Premium member)");
        return basePrice;
    }

    @Override
    protected double applyDiscount(double price) {
        // Overrides the hook — applies 10% loyalty discount
        double discount = price * LOYALTY_DISCOUNT_PERCENT / 100;
        double discounted = price - discount;
        System.out.println("[Discount]  Loyalty 10% applied: -Rs." + String.format("%.2f", discount) + " → Rs." + String.format("%.2f", discounted));
        return discounted;
    }

    @Override
    protected void processPayment(String orderId, double amount) {
        System.out.println("[Payment]   Priority payment of Rs." + String.format("%.2f", amount) + " processed for " + orderId + " (Premium queue)");
    }
}
