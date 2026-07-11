package com.awinas.learning.dp.structural.adapter.thirdparty;

// Third-party PayPal SDK - cannot modify
public class PaypalSDK {

    public String executePayment(String orderId, double amount) {
        String paymentId = "PPL-" + System.currentTimeMillis();
        System.out.println("[PaypalSDK] executePayment() | orderId=" + orderId
                + " | amount=" + amount + " | paymentId=" + paymentId);
        return paymentId;
    }

    public boolean issueRefund(String paymentId) {
        System.out.println("[PaypalSDK] issueRefund() | paymentId=" + paymentId);
        return true;
    }
}
