package com.awinas.learning.dp.structural.facade.subsystem;

// Subsystem — creates shipment and assigns tracking
public class ShippingService {

    public String createShipment(String orderId, String address) {
        String trackingId = "TRK-" + orderId;
        System.out.println("  [ShippingService]  Shipment created for order " + orderId);
        System.out.println("  [ShippingService]  Deliver to: " + address);
        System.out.println("  [ShippingService]  Tracking ID: " + trackingId);
        return trackingId;
    }

    public void cancelShipment(String trackingId) {
        System.out.println("  [ShippingService]  Shipment cancelled | Tracking: " + trackingId);
    }
}
