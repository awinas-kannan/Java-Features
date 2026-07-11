package com.awinas.learning.designpatterns.paymentapp.sdk;

/**
 * Third-party Razorpay SDK - we CANNOT modify this.
 *
 * Notice the method names and signatures are completely
 * different from our internal PaymentService interface.
 * That is exactly why we need an Adapter.
 */
public class RazorpaySDK {

    public String razorpayPay(double amount, String method, String details) {
        String txnId = "RZP-" + System.currentTimeMillis();
        System.out.println("  [RazorpaySDK] razorpayPay() -> amount=" + amount
                + " | method=" + method + " | details=" + details
                + " | txnId=" + txnId);
        return txnId;
    }

    public boolean razorpayRefund(String transactionId, double amount) {
        System.out.println("  [RazorpaySDK] razorpayRefund() -> txnId=" + transactionId
                + " | amount=" + amount);
        return true;
    }
}
