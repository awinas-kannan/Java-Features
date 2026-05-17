package com.awinas.learning.solid.walmartreturns.interfacesegregation.bad;

/*
 * ============================================================================
 * INTERFACE SEGREGATION PRINCIPLE (ISP) - BAD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "No client should be forced to depend on methods it does not use."
 *   — Robert C. Martin
 *
 * In simpler terms: Don't create "fat" interfaces that force implementing
 * classes to provide methods they don't need.
 *
 * ============================================================================
 * WALMART RETURNS CONTEXT:
 * ============================================================================
 * In the returns domain, there are different "handlers" that deal with returns:
 *
 *   - Store Associate       → Processes in-store returns, inspects items physically
 *   - Online Returns Bot    → Handles online return requests, generates labels, tracks shipping
 *   - Marketplace Handler   → Routes to third-party seller, handles seller communication
 *   - Analytics Service     → Only reads return data for reporting, never processes anything
 *
 * ============================================================================
 * WHY IS THIS BAD?
 * ============================================================================
 *
 * ❌ This ONE "fat" interface forces ALL return handlers to implement EVERY method,
 *    even if they don't need them.
 *
 * ❌ AnalyticsService is forced to implement processRefund(), inspectItem(),
 *    generateShippingLabel() — none of which it actually does!
 *
 * ❌ StoreAssociate is forced to implement generateShippingLabel() and
 *    contactThirdPartySeller() — irrelevant to in-store operations!
 *
 * ❌ Results in dummy/empty implementations or UnsupportedOperationException.
 *
 * ❌ Changes to the fat interface ripple to ALL implementors, even unrelated ones.
 * ============================================================================
 */
public interface IReturnHandler {

    void processRefund(String orderId, double amount);

    void inspectItemCondition(String orderId);

    void generateShippingLabel(String orderId);

    void trackShipment(String trackingId);

    void contactThirdPartySeller(String sellerId, String message);

    void generateReturnReport(String orderId);

    void sendCustomerNotification(String customerId, String message);
}
