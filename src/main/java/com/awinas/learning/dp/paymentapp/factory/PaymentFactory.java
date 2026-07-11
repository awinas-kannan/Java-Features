package com.awinas.learning.dp.paymentapp.factory;

import com.awinas.learning.dp.paymentapp.abstractfactory.PaymentProviderFactory;
import com.awinas.learning.dp.paymentapp.abstractfactory.RazorpayFactory;
import com.awinas.learning.dp.paymentapp.abstractfactory.StripeFactory;

/**
 * FACTORY PATTERN - Payment Factory
 *
 * Creates the right PaymentProviderFactory (Abstract Factory) by provider name.
 *
 * Relationship between Factory and Abstract Factory:
 *   - Factory     = decides WHICH provider factory to create (based on string/config)
 *   - Abstract Factory = creates the FAMILY of objects for that provider
 *
 * Caller only needs to say: PaymentFactory.getFactory("RAZORPAY")
 * They don't import RazorpayFactory or StripeFactory directly.
 *
 * Adding a new provider (e.g., Adyen):
 *   1. Create AdyenFactory implements PaymentProviderFactory
 *   2. Add "ADYEN" case here
 *   -> OrderService needs NO changes (Open/Closed Principle)
 */
public class PaymentFactory {

    public static PaymentProviderFactory getFactory(String provider) {
        switch (provider.toUpperCase()) {
            case "RAZORPAY":
                return new RazorpayFactory();
            case "STRIPE":
                return new StripeFactory();
            default:
                throw new IllegalArgumentException("Unsupported payment provider: " + provider);
        }
    }
}
