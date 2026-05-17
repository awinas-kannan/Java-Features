package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

/**
 * ✅ Online returns bot implements ONLY the interfaces relevant to online returns:
 *    - RefundProcessor (processes refunds after item received)
 *    - ShippingHandler (generates labels, tracks packages)
 *    - CustomerNotifier (sends email/SMS updates)
 *
 * Does NOT implement ItemInspector or SellerCommunicator — not relevant online!
 */
public class OnlineReturnsBot implements RefundProcessor, ShippingHandler, CustomerNotifier {

    @Override
    public void processRefund(String orderId, double amount) {
        System.out.println("🤖 Online bot processing refund of $" + amount + " for " + orderId);
    }

    @Override
    public void generateShippingLabel(String orderId) {
        System.out.println("📦 Online bot generating shipping label for " + orderId);
    }

    @Override
    public void trackShipment(String trackingId) {
        System.out.println("🚚 Online bot tracking shipment: " + trackingId);
    }

    @Override
    public void sendCustomerNotification(String customerId, String message) {
        System.out.println("📧 Online bot emailing customer " + customerId + ": " + message);
    }
}
