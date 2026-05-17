package com.awinas.learning.solid.walmartreturns.singleresponsibility.good;

/**
 * SOLE RESPONSIBILITY: Send notifications to the customer.
 *
 * This class ONLY changes if the notification mechanism changes
 * (e.g., adding push notifications, switching email provider, SMS format change).
 */
public class NotificationService {

    public void notifyCustomer(ReturnRequest request) {
        System.out.println("📧 Email sent to customer " + request.getCustomerId() +
                ": Your return for '" + request.getItemName() + "' has been processed.");
        System.out.println("📱 SMS sent to customer " + request.getCustomerId() +
                ": Refund initiated for order " + request.getOrderId());
    }
}
