package com.awinas.learning.dp.structural.proxy;

public interface PaymentGateway {
    String pay(String userId, double amount);
    boolean refund(String transactionId, double amount);
}
