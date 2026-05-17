package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

/**
 * ✅ Swap-in Cassandra without touching ReturnService — DIP in action!
 */
public class CassandraReturnRepository implements ReturnRepository {

    @Override
    public void saveReturn(String orderId, String customerId, double refundAmount) {
        System.out.println("[Cassandra] Saving return - Order: " + orderId +
                ", Customer: " + customerId + ", Refund: $" + refundAmount);
    }
}
