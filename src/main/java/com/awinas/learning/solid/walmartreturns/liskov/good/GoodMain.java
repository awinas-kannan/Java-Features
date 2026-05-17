package com.awinas.learning.solid.walmartreturns.liskov.good;

import java.util.List;

/**
 * Demonstrates LSP-compliant design — all substitutions are SAFE.
 *
 * Any Refundable can be passed where a Refundable is expected.
 * Any Shippable can be passed where a Shippable is expected.
 * No runtime exceptions, no surprises.
 */
public class GoodMain {

    public static void processAllRefunds(List<Refundable> refundables) {
        System.out.println("=== Processing All Refunds (LSP Safe!) ===");
        for (Refundable item : refundables) {
            item.processRefund(); // ✅ SAFE — every Refundable implementation supports this
        }
    }

    public static void generateAllShippingLabels(List<Shippable> shippables) {
        System.out.println("\n=== Generating Shipping Labels (LSP Safe!) ===");
        for (Shippable item : shippables) {
            item.generateShippingLabel(); // ✅ SAFE — every Shippable implementation supports this
            System.out.println("   Tracking: " + item.getTrackingInfo());
        }
    }

    public static void main(String[] args) {
        // All refundable items — InStoreReturn, OnlineReturn, CurbsideReturn
        // ALL safely substitute for Refundable. No exceptions thrown!
        List<Refundable> allRefunds = List.of(
                new OnlineReturn("WMT-001", 49.99),
                new InStoreReturn("WMT-002", 29.99),
                new CurbsideReturn("WMT-003", 19.99)
        );
        processAllRefunds(allRefunds);

        // Only shippable items — code that needs shipping only accepts Shippable
        List<Shippable> shippableReturns = List.of(
                new OnlineReturn("WMT-001", 49.99)
        );
        generateAllShippingLabels(shippableReturns);

        // ✅ Notice: InStoreReturn is NEVER passed to generateAllShippingLabels()
        //    because it doesn't implement Shippable. The compiler prevents the mistake!
    }
}
