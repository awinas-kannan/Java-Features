package com.awinas.learning.dp.behavioural.strategy;

public interface PaymentStrategy {
    void pay(double amount);      // behaviour — executes the payment
    String getMethod();           // metadata — used for logging / SDK integration
    String getDetails();          // metadata — method-specific info
}
