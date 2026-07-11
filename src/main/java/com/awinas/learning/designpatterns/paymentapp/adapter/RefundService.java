package com.awinas.learning.designpatterns.paymentapp.adapter;

/**
 * ADAPTER PATTERN - Target Interface (Refund)
 *
 * Our application calls refund(transactionId, amount) uniformly.
 * Each adapter translates this into the SDK-specific refund call.
 */
public interface RefundService {
    boolean refund(String transactionId, double amount);
}
