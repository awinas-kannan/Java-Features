package com.awinas.learning.solid.walmartreturns.dependencyinversion.bad;

public class MySQLReturnRepository {

    public void saveReturn(String orderId, String customerId, double refundAmount) {
        System.out.println("[MySQL] Saving return - Order: " + orderId +
                ", Customer: " + customerId + ", Refund: $" + refundAmount);
    }
}
