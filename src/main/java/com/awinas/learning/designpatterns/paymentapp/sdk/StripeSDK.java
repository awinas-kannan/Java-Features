package com.awinas.learning.designpatterns.paymentapp.sdk;

/**
 * Third-party Stripe SDK - we CANNOT modify this.
 *
 * Stripe uses "stripeCharge" and "stripeRefund" — completely different
 * from Razorpay and from our internal PaymentService interface.
 * The Adapter bridges this gap.
 */
public class StripeSDK {

    public String stripeCharge(double amount, String paymentMethod, String details) {
        String chargeId = "STR-" + System.currentTimeMillis();
        System.out.println("  [StripeSDK] stripeCharge() -> amount=" + amount
                + " | method=" + paymentMethod + " | details=" + details
                + " | chargeId=" + chargeId);
        return chargeId;
    }

    public boolean stripeRefund(String chargeId, double amount) {
        System.out.println("  [StripeSDK] stripeRefund() -> chargeId=" + chargeId
                + " | amount=" + amount);
        return true;
    }
}
