package com.awinas.learning.dp.behavioural.templatemethod;

// International order — international shipping + customs duty, no domestic discounts, international payment
public class InternationalOrderProcessor extends OrderProcessor {

    private static final double INTERNATIONAL_SHIPPING = 499.0;
    private static final double CUSTOMS_DUTY_PERCENT   = 18.0;

    @Override
    protected double calculatePrice(double basePrice) {
        double customs = basePrice * CUSTOMS_DUTY_PERCENT / 100;
        double total = basePrice + INTERNATIONAL_SHIPPING + customs;
        System.out.println("[Price]     Base: Rs." + basePrice
                + " + Intl Shipping: Rs." + INTERNATIONAL_SHIPPING
                + " + Customs " + CUSTOMS_DUTY_PERCENT + "%: Rs." + String.format("%.2f", customs)
                + " = Rs." + String.format("%.2f", total));
        return total;
    }

    // No discount override — international orders don't get domestic discounts

    @Override
    protected void processPayment(String orderId, double amount) {
        System.out.println("[Payment]   International payment of Rs." + String.format("%.2f", amount)
                + " processed via forex gateway for " + orderId);
    }
}
