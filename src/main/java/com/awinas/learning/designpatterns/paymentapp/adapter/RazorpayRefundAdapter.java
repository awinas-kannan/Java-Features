package com.awinas.learning.designpatterns.paymentapp.adapter;

import com.awinas.learning.designpatterns.paymentapp.sdk.RazorpaySDK;

/**
 * ADAPTER PATTERN - Razorpay Refund Adapter
 *
 * Translates:
 *   RefundService.refund(transactionId, amount)
 *     -> RazorpaySDK.razorpayRefund(transactionId, amount)
 */
public class RazorpayRefundAdapter implements RefundService {

    private final RazorpaySDK razorpaySDK;

    public RazorpayRefundAdapter(RazorpaySDK razorpaySDK) {
        this.razorpaySDK = razorpaySDK;
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        return razorpaySDK.razorpayRefund(transactionId, amount);
    }
}
