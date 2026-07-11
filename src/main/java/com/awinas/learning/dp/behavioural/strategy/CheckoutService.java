package com.awinas.learning.dp.behavioural.strategy;

// Context — delegates payment to whichever strategy is set.
// CheckoutService never changes when a new payment method is added.
public class CheckoutService {

    private PaymentStrategy paymentStrategy;

    public CheckoutService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Strategy can be swapped at runtime
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void pay(String orderId, double amount) {
        System.out.println("[CheckoutService] Processing order: " + orderId + " | Rs." + amount);
        paymentStrategy.pay(amount);   // delegate — doesn't know or care which strategy runs
    }
}
