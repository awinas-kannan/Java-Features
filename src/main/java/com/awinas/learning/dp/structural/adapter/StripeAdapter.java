package com.awinas.learning.dp.structural.adapter;

import com.awinas.learning.dp.structural.adapter.thirdparty.StripeSDK;

public class StripeAdapter implements PaymentService {

    private final StripeSDK stripeSDK;

    public StripeAdapter(StripeSDK stripeSDK) {
        this.stripeSDK = stripeSDK;
    }

    @Override
    public String pay(double amount, String method) {
        // Adapt: pay() -> stripeSDK.stripeCharge()
        return stripeSDK.stripeCharge(amount, method);
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        // Adapt: refund() -> stripeSDK.stripeRefund()
        return stripeSDK.stripeRefund(transactionId, amount);
    }
}
