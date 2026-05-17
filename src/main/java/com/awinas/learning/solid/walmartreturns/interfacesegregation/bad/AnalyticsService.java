package com.awinas.learning.solid.walmartreturns.interfacesegregation.bad;

/**
 * ❌ Analytics service ONLY needs to generate reports, but is forced to implement
 * ALL 7 methods from the fat interface. 6 out of 7 are useless empty stubs!
 */
public class AnalyticsService implements IReturnHandler {

    @Override
    public void processRefund(String orderId, double amount) {
        // ❌ Analytics doesn't process refunds! But forced to implement.
        throw new UnsupportedOperationException("Analytics doesn't process refunds!");
    }

    @Override
    public void inspectItemCondition(String orderId) {
        // ❌ Analytics doesn't inspect items!
        throw new UnsupportedOperationException("Analytics doesn't inspect items!");
    }

    @Override
    public void generateShippingLabel(String orderId) {
        // ❌ Analytics doesn't generate labels!
        throw new UnsupportedOperationException("Analytics doesn't generate labels!");
    }

    @Override
    public void trackShipment(String trackingId) {
        // ❌ Analytics doesn't track shipments!
        throw new UnsupportedOperationException("Analytics doesn't track shipments!");
    }

    @Override
    public void contactThirdPartySeller(String sellerId, String message) {
        // ❌ Analytics doesn't contact sellers!
        throw new UnsupportedOperationException("Analytics doesn't contact sellers!");
    }

    @Override
    public void generateReturnReport(String orderId) {
        // ✅ This is the ONLY method Analytics actually needs!
        System.out.println("📊 Analytics generating return report for " + orderId);
    }

    @Override
    public void sendCustomerNotification(String customerId, String message) {
        // ❌ Analytics doesn't send notifications!
        throw new UnsupportedOperationException("Analytics doesn't send notifications!");
    }
}
