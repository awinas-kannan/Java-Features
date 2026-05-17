package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

public class MySQLReturnRepository implements ReturnRepository {

    @Override
    public void saveReturn(String orderId, String customerId, double refundAmount) {
        System.out.println("[MySQL] Saving return - Order: " + orderId +
                ", Customer: " + customerId + ", Refund: $" + refundAmount);
    }
}
