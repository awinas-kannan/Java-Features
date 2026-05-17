package com.awinas.learning.solid.walmartreturns.liskov.good;

/**
 * Separate interface for items that require shipping as part of the return process.
 * Only return types that genuinely support shipping will implement this.
 */
public interface Shippable {

    void generateShippingLabel();

    String getTrackingInfo();
}
