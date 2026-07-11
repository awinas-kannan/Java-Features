package com.awinas.learning.dp.paymentapp.abstractfactory;

import com.awinas.learning.dp.paymentapp.adapter.PaymentService;
import com.awinas.learning.dp.paymentapp.adapter.RefundService;

/**
 * ABSTRACT FACTORY PATTERN - Abstract Factory Interface
 *
 * Creates a FAMILY of related objects for one payment provider.
 *
 * Key guarantee: both objects returned always belong to the SAME provider.
 * You will never accidentally get a Stripe PaymentService with a Razorpay RefundService.
 *
 * Concrete factories:
 *   - RazorpayFactory  -> RazorpayPaymentAdapter + RazorpayRefundAdapter
 *   - StripeFactory    -> StripePaymentAdapter   + StripeRefundAdapter
 */
public interface PaymentProviderFactory {
    PaymentService createPaymentService();
    RefundService  createRefundService();
}
