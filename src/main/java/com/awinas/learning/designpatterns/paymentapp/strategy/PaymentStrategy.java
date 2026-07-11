package com.awinas.learning.designpatterns.paymentapp.strategy;

/**
 * STRATEGY PATTERN - Strategy Interface
 *
 * Defines the payment method algorithm.
 * Each strategy carries the method type and the relevant details.
 *
 * Concrete strategies: UpiStrategy, CardStrategy, NetBankingStrategy
 *
 * The Adapter uses getMethod() + getDetails() to forward to the SDK.
 * Switching payment method = just swap the strategy. Nothing else changes.
 */
public interface PaymentStrategy {
    String getMethod();   // "UPI" | "CARD" | "NETBANKING"
    String getDetails();  // UPI ID, masked card, bank name, etc.
}
