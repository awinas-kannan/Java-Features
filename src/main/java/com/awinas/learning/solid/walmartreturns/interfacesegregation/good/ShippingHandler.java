package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

public interface ShippingHandler {
    void generateShippingLabel(String orderId);
    void trackShipment(String trackingId);
}
