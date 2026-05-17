package com.awinas.learning.solid.walmartreturns.interfacesegregation.bad;

/**
 * ❌ Store associate is FORCED to implement shipping and marketplace methods
 * that are completely irrelevant to in-store operations.
 */
public class StoreAssociate implements IReturnHandler {

    @Override
    public void processRefund(String orderId, double amount) {
        System.out.println("🏪 Store associate processing refund of $" + amount + " for " + orderId);
    }

    @Override
    public void inspectItemCondition(String orderId) {
        System.out.println("🔍 Store associate inspecting item condition for " + orderId);
    }

    @Override
    public void generateShippingLabel(String orderId) {
        // ❌ FORCED to implement — store associates don't generate shipping labels!
        throw new UnsupportedOperationException("Store associates don't handle shipping!");
    }

    @Override
    public void trackShipment(String trackingId) {
        // ❌ FORCED to implement — irrelevant for in-store returns!
        throw new UnsupportedOperationException("No shipments to track in-store!");
    }

    @Override
    public void contactThirdPartySeller(String sellerId, String message) {
        // ❌ FORCED to implement — store associates don't deal with marketplace sellers!
        throw new UnsupportedOperationException("Store associates don't contact sellers!");
    }

    @Override
    public void generateReturnReport(String orderId) {
        System.out.println("📋 Store associate generating return slip for " + orderId);
    }

    @Override
    public void sendCustomerNotification(String customerId, String message) {
        System.out.println("📢 Store associate notifying customer " + customerId);
    }
}
