package com.awinas.learning.solid.walmartreturns.singleresponsibility.good;

/**
 * SOLE RESPONSIBILITY: Log return events for audit and analytics.
 *
 * This class ONLY changes if the logging format, destination, or audit requirements change
 * (e.g., switch from console to Kafka, add new fields for compliance).
 */
public class ReturnAuditLogger {

    public void log(ReturnRequest request) {
        System.out.println("📊 [AUDIT LOG] Return processed - Order: " + request.getOrderId() +
                ", Customer: " + request.getCustomerId() +
                ", Item: " + request.getItemName() +
                ", Amount: $" + request.getItemPrice() +
                ", Days Since Purchase: " + request.getDaysSincePurchase());
    }
}
