package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

import java.util.List;

/**
 * Demonstrates how ISP-compliant code is clean, focused, and type-safe.
 * Each method only accepts the interface it needs — no fat dependencies.
 */
public class GoodMain {

    public static void processRefunds(List<RefundProcessor> processors) {
        System.out.println("=== Processing Refunds ===");
        for (RefundProcessor processor : processors) {
            processor.processRefund("WMT-ORD-001", 59.99);
        }
    }

    public static void generateShippingLabels(List<ShippingHandler> handlers) {
        System.out.println("\n=== Generating Shipping Labels ===");
        for (ShippingHandler handler : handlers) {
            handler.generateShippingLabel("WMT-ORD-001");
        }
    }

    public static void generateReports(List<ReportGenerator> generators) {
        System.out.println("\n=== Generating Reports ===");
        for (ReportGenerator generator : generators) {
            generator.generateReturnReport("WMT-ORD-001");
        }
    }

    public static void main(String[] args) {
        StoreAssociate storeAssociate = new StoreAssociate();
        OnlineReturnsBot onlineBot = new OnlineReturnsBot();
        MarketplaceHandler marketplaceHandler = new MarketplaceHandler();
        AnalyticsService analytics = new AnalyticsService();

        // ✅ Only refund processors are passed to refund logic
        processRefunds(List.of(storeAssociate, onlineBot, marketplaceHandler));

        // ✅ Only shipping-capable handlers are passed to shipping logic
        generateShippingLabels(List.of(onlineBot));

        // ✅ Only report generators are passed to report logic
        generateReports(List.of(analytics));

        // ✅ Compile-time safety: analytics CANNOT be passed to processRefunds()
        //    because it doesn't implement RefundProcessor!
    }
}
