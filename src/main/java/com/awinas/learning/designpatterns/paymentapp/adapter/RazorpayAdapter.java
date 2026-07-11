package com.awinas.learning.designpatterns.paymentapp.adapter;

import com.awinas.learning.designpatterns.paymentapp.sdk.RazorpaySDK;
import com.awinas.learning.designpatterns.paymentapp.strategy.PaymentStrategy;

/**
 * ADAPTER PATTERN - Razorpay Adapter
 *
 * Implements both PaymentService and RefundService because:
 *   - Both use the same RazorpaySDK instance
 *   - Payment and refund on the same SDK change together
 *   - No reason to split — single class is the right level of abstraction
 *
 * Translates:
 *   pay(amount, strategy)      ->  razorpaySDK.razorpayPay(amount, method, details)
 *   refund(txnId, amount)      ->  razorpaySDK.razorpayRefund(txnId, amount)
 */
public class RazorpayAdapter implements PaymentService, RefundService {

    private final RazorpaySDK razorpaySDK;

    public RazorpayAdapter(RazorpaySDK razorpaySDK) {
        this.razorpaySDK = razorpaySDK;
    }

    @Override
    public String pay(double amount, PaymentStrategy strategy) {
        return razorpaySDK.razorpayPay(amount, strategy.getMethod(), strategy.getDetails());
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        return razorpaySDK.razorpayRefund(transactionId, amount);
    }
}
