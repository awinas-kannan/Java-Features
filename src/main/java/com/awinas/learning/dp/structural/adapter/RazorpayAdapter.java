package com.awinas.learning.dp.structural.adapter;

import com.awinas.learning.dp.structural.adapter.thirdparty.RazorpaySDK;

public class RazorpayAdapter implements PaymentService {

    private final RazorpaySDK razorpaySDK;

    public RazorpayAdapter(RazorpaySDK razorpaySDK) {
        this.razorpaySDK = razorpaySDK;
    }

    @Override
    public String pay(double amount, String method) {
        // Adapt: pay() -> razorpaySDK.razorpayPay()
        return razorpaySDK.razorpayPay(amount, method);
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        // Adapt: refund() -> razorpaySDK.razorpayRefund()
        return razorpaySDK.razorpayRefund(transactionId, amount);
    }
}
