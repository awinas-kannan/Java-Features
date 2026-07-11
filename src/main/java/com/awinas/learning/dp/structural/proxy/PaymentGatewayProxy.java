package com.awinas.learning.dp.structural.proxy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Proxy - sits in front of RealPaymentGateway
// Adds: limit check, fraud check, logging
// The caller never knows a proxy is involved
public class PaymentGatewayProxy implements PaymentGateway {

    private static final double DAILY_LIMIT      = 10_000.0;
    private static final double SINGLE_TXN_LIMIT =  5_000.0;

    private final PaymentGateway realGateway;
    private final Map<String, Double> dailySpend = new HashMap<>();

    public PaymentGatewayProxy(PaymentGateway realGateway) {
        this.realGateway = realGateway;
    }

    @Override
    public String pay(String userId, double amount) {

        log("PAY request | User: " + userId + " | Amount: Rs." + amount);

        // Check 1: Single transaction limit
        if (amount > SINGLE_TXN_LIMIT) {
            log("BLOCKED - Single transaction limit Rs." + SINGLE_TXN_LIMIT + " exceeded");
            return null;
        }

        // Check 2: Daily spend limit
        double spent = dailySpend.getOrDefault(userId, 0.0);
        if (spent + amount > DAILY_LIMIT) {
            log("BLOCKED - Daily limit Rs." + DAILY_LIMIT + " exceeded. Already spent: Rs." + spent);
            return null;
        }

        // All checks passed — delegate to real gateway
        String txnId = realGateway.pay(userId, amount);

        // Update daily spend tracking
        dailySpend.put(userId, spent + amount);

        log("PAY success | TxnId: " + txnId + " | Total today: Rs." + (spent + amount));
        return txnId;
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        log("REFUND request | TxnId: " + transactionId + " | Amount: Rs." + amount);

        boolean result = realGateway.refund(transactionId, amount);

        log("REFUND " + (result ? "success" : "failed") + " | TxnId: " + transactionId);
        return result;
    }

    private void log(String message) {
        System.out.println("[Proxy " + LocalDateTime.now().toLocalTime() + "] " + message);
    }
}
