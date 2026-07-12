package com.awinas.learning.dp.structural.facade.subsystem;

// Subsystem — processes payment
public class PaymentService {

    public String processPayment(String orderId, double amount, String method) {
        String txnId = "TXN-" + System.currentTimeMillis();
        System.out.println("  [PaymentService]   Processing " + method + " payment of Rs." + amount + " for order " + orderId);
        System.out.println("  [PaymentService]   Transaction ID: " + txnId);
        return txnId;
    }

    public void refundPayment(String txnId, double amount) {
        System.out.println("  [PaymentService]   Refunding Rs." + amount + " | TxnId: " + txnId);
    }
}
