package com.awinas.learning.designpatterns.paymentapp.abstractfactory;

import com.awinas.learning.designpatterns.paymentapp.adapter.PaymentService;
import com.awinas.learning.designpatterns.paymentapp.adapter.RefundService;
import com.awinas.learning.designpatterns.paymentapp.adapter.RazorpayPaymentAdapter;
import com.awinas.learning.designpatterns.paymentapp.adapter.RazorpayRefundAdapter;
import com.awinas.learning.designpatterns.paymentapp.sdk.RazorpaySDK;

/**
 * ABSTRACT FACTORY PATTERN - Razorpay Concrete Factory
 *
 * Creates the Razorpay ecosystem:
 *   - RazorpayPaymentAdapter (Adapter wrapping RazorpaySDK)
 *   - RazorpayRefundAdapter  (Adapter wrapping RazorpaySDK)
 *
 * Both share the same RazorpaySDK instance.
 */
public class RazorpayFactory implements PaymentProviderFactory {

    private final RazorpaySDK razorpaySDK = new RazorpaySDK();

    @Override
    public PaymentService createPaymentService() {
        return new RazorpayPaymentAdapter(razorpaySDK);
    }

    @Override
    public RefundService createRefundService() {
        return new RazorpayRefundAdapter(razorpaySDK);
    }
}
