package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

/*
 * ============================================================================
 * INTERFACE SEGREGATION PRINCIPLE (ISP) - GOOD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "No client should be forced to depend on methods it does not use."
 *
 * ============================================================================
 * GOOD DESIGN — Split the fat interface into focused, cohesive interfaces:
 * ============================================================================
 *
 *   RefundProcessor         → Only refund-related methods
 *   ItemInspector           → Only item inspection methods
 *   ShippingHandler         → Only shipping-related methods
 *   SellerCommunicator      → Only marketplace seller communication
 *   ReportGenerator         → Only reporting methods
 *   CustomerNotifier        → Only notification methods
 *
 * Each class implements ONLY the interfaces whose methods it genuinely uses.
 *
 * ============================================================================
 * WHY IS THIS GOOD?
 * ============================================================================
 *
 * ✅ StoreAssociate implements: RefundProcessor, ItemInspector, CustomerNotifier
 *    — Only what it actually does. No dead methods!
 *
 * ✅ OnlineReturnsBot implements: RefundProcessor, ShippingHandler, CustomerNotifier
 *    — Only what online returns need.
 *
 * ✅ AnalyticsService implements: ReportGenerator
 *    — Just the one interface it needs. Clean and focused!
 *
 * ✅ Changes to ShippingHandler don't affect AnalyticsService or StoreAssociate.
 *
 * ✅ No empty stubs, no UnsupportedOperationException, no pollution.
 * ============================================================================
 */
public interface RefundProcessor {
    void processRefund(String orderId, double amount);
}
