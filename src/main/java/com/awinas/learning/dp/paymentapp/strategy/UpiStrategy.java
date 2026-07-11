package com.awinas.learning.dp.paymentapp.strategy;

/**
 * STRATEGY PATTERN - UPI Payment Strategy
 *
 * Encapsulates the UPI payment method.
 * The UPI ID is the only detail needed.
 */
public class UpiStrategy implements PaymentStrategy {

    private final String upiId;

    public UpiStrategy(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public String getMethod() {
        return "UPI";
    }

    @Override
    public String getDetails() {
        return "UPI ID: " + upiId;
    }
}
