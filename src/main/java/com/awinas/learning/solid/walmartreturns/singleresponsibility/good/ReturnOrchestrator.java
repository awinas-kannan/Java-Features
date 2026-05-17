package com.awinas.learning.solid.walmartreturns.singleresponsibility.good;

/**
 * SOLE RESPONSIBILITY: Orchestrate the return workflow by delegating to focused services.
 *
 * This is a thin coordination layer. It ONLY changes if the workflow steps change
 * (e.g., add a fraud-check step before processing refund).
 */
public class ReturnOrchestrator {

    private final ReturnValidator validator;
    private final RefundProcessor refundProcessor;
    private final NotificationService notificationService;
    private final ReturnAuditLogger auditLogger;

    public ReturnOrchestrator() {
        this.validator = new ReturnValidator();
        this.refundProcessor = new RefundProcessor();
        this.notificationService = new NotificationService();
        this.auditLogger = new ReturnAuditLogger();
    }

    public void initiateReturn(ReturnRequest request) {
        if (validator.isEligible(request)) {
            refundProcessor.processRefund(request);
            notificationService.notifyCustomer(request);
            auditLogger.log(request);
        }
    }

    public static void main(String[] args) {
        ReturnOrchestrator orchestrator = new ReturnOrchestrator();

        ReturnRequest validReturn = new ReturnRequest(
                "WMT-ORD-98765", "CUST-1234", "Samsung TV 55\"", 499.99, 25);
        orchestrator.initiateReturn(validReturn);

        System.out.println("\n--- Attempting expired return ---\n");

        ReturnRequest expiredReturn = new ReturnRequest(
                "WMT-ORD-11111", "CUST-5678", "Blender", 79.99, 120);
        orchestrator.initiateReturn(expiredReturn);
    }
}
