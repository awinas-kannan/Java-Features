package com.awinas.learning.dp.creational.factorymethod;

/**
 * Factory Method Pattern - Retail Example
 *
 * Common interface for all payment processors.
 */
public interface PaymentProcessor {

    void processPayment(double amount);

    String getPaymentMethod();
}
