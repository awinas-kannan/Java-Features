package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

/**
 * ✅ Analytics service implements ONLY ReportGenerator — the one thing it does.
 * No forced stubs, no UnsupportedOperationException, no dead code.
 * Clean, focused, and easy to maintain.
 */
public class AnalyticsService implements ReportGenerator {

    @Override
    public void generateReturnReport(String orderId) {
        System.out.println("📊 Analytics generating comprehensive return report for " + orderId);
    }
}
