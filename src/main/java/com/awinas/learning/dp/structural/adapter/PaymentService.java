package com.awinas.learning.dp.structural.adapter;

public interface PaymentService {
    String pay(double amount, String method);
    boolean refund(String transactionId, double amount);
}
