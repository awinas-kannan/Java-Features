package com.awinas.learning.dp.structural.adapter;

import com.awinas.learning.dp.structural.adapter.thirdparty.PaypalSDK;

public class PaypalAdapter implements PaymentService {

    private final PaypalSDK paypalSDK;

    public PaypalAdapter(PaypalSDK paypalSDK) {
        this.paypalSDK = paypalSDK;
    }

    @Override
    public String pay(double amount, String method) {
        // Adapt: pay() -> paypalSDK.executePayment()
        String orderId = "ORD-" + method + "-" + System.currentTimeMillis();
        return paypalSDK.executePayment(orderId, amount);
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        // Adapt: refund() -> paypalSDK.issueRefund()
        // PayPal refund doesn't need amount — adapter absorbs that difference
        return paypalSDK.issueRefund(transactionId);
    }
}
