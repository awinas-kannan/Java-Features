package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

/**
 * ✅ Store associate implements ONLY the interfaces relevant to in-store operations:
 *    - RefundProcessor (processes refunds at the counter)
 *    - ItemInspector (physically checks item condition)
 *    - CustomerNotifier (informs customer about return status)
 *
 * Does NOT implement ShippingHandler or SellerCommunicator — not their job!
 */
public class StoreAssociate implements RefundProcessor, ItemInspector, CustomerNotifier {

    @Override
    public void processRefund(String orderId, double amount) {
        System.out.println("🏪 Store associate processing refund of $" + amount + " for " + orderId);
    }

    @Override
    public void inspectItemCondition(String orderId) {
        System.out.println("🔍 Store associate inspecting item condition for " + orderId);
    }

    @Override
    public void sendCustomerNotification(String customerId, String message) {
        System.out.println("📢 Store associate notifying customer " + customerId + ": " + message);
    }
}
