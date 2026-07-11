package com.awinas.learning.dp.structural.adapter.thirdparty;

// Third-party Razorpay SDK - cannot modify
public class RazorpaySDK {

    public String razorpayPay(double amount, String paymentMethod) {
        String txnId = "RZP-" + System.currentTimeMillis();
        System.out.println("[RazorpaySDK] razorpayPay() | amount=" + amount
                + " | method=" + paymentMethod + " | txnId=" + txnId);
        return txnId;
    }

    public boolean razorpayRefund(String transactionId, double amount) {
        System.out.println("[RazorpaySDK] razorpayRefund() | txnId=" + transactionId
                + " | amount=" + amount);
        return true;
    }
}
