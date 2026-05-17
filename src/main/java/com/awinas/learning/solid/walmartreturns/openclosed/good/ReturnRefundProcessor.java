package com.awinas.learning.solid.walmartreturns.openclosed.good;

/**
 * This processor is CLOSED for modification — it never changes when new return types are added.
 * New return types simply implement RefundStrategy and are passed in.
 */
public class ReturnRefundProcessor {

    public double processRefund(RefundStrategy strategy, double itemPrice, int daysSincePurchase) {
        double refundAmount = strategy.calculateRefund(itemPrice, daysSincePurchase);
        System.out.printf("[%s] Refund calculated: $%.2f for item priced at $%.2f%n",
                strategy.getReturnType(), refundAmount, itemPrice);
        return refundAmount;
    }

    public static void main(String[] args) {
        ReturnRefundProcessor processor = new ReturnRefundProcessor();

        processor.processRefund(new InStoreRefund(), 100.0, 15);
        processor.processRefund(new OnlineRefund(), 100.0, 45);
        processor.processRefund(new CurbsideRefund(), 100.0, 10);
        processor.processRefund(new MarketplaceRefund(), 100.0, 5);

        // ✅ Adding a new return type requires ZERO changes to existing code:
        // Just create: LockerReturnRefund implements RefundStrategy
        // and call: processor.processRefund(new LockerReturnRefund(), 100.0, 7);
    }
}
