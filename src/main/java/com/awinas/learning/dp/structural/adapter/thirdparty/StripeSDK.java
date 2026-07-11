package com.awinas.learning.dp.structural.adapter.thirdparty;

// Third-party Stripe SDK - cannot modify
public class StripeSDK {

    public String stripeCharge(double amount, String paymentMethod) {
        String chargeId = "STR-" + System.currentTimeMillis();
        System.out.println("[StripeSDK] stripeCharge() | amount=" + amount
                + " | method=" + paymentMethod + " | chargeId=" + chargeId);
        return chargeId;
    }

    public boolean stripeRefund(String chargeId, double amount) {
        System.out.println("[StripeSDK] stripeRefund() | chargeId=" + chargeId
                + " | amount=" + amount);
        return true;
    }
}
