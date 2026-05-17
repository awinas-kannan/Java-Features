package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

/**
 * ✅ Marketplace handler implements ONLY interfaces for marketplace returns:
 *    - RefundProcessor (coordinates refund with seller)
 *    - SellerCommunicator (communicates with 3rd party seller)
 *    - CustomerNotifier (keeps customer updated)
 */
public class MarketplaceHandler implements RefundProcessor, SellerCommunicator, CustomerNotifier {

    @Override
    public void processRefund(String orderId, double amount) {
        System.out.println("🏬 Marketplace handler initiating seller refund of $" + amount);
    }

    @Override
    public void contactThirdPartySeller(String sellerId, String message) {
        System.out.println("📞 Marketplace handler contacting seller " + sellerId + ": " + message);
    }

    @Override
    public void sendCustomerNotification(String customerId, String message) {
        System.out.println("📧 Marketplace handler notifying customer " + customerId + ": " + message);
    }
}
