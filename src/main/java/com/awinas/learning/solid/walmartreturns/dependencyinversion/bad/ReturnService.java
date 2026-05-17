package com.awinas.learning.solid.walmartreturns.dependencyinversion.bad;

/*
 * ============================================================================
 * DEPENDENCY INVERSION PRINCIPLE (DIP) - BAD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "High-level modules should NOT depend on low-level modules.
 *    Both should depend on ABSTRACTIONS (interfaces)."
 *   — Robert C. Martin
 *
 * Also:
 *   "Abstractions should NOT depend on details.
 *    Details should depend on abstractions."
 *
 * ============================================================================
 * WALMART RETURNS CONTEXT:
 * ============================================================================
 * When processing a return, the ReturnService (high-level) needs to:
 *   - Store return data in a database
 *   - Send notifications to the customer
 *   - Calculate refunds
 *
 * ============================================================================
 * WHY IS THIS BAD?
 * ============================================================================
 *
 * ❌ ReturnService (high-level module) directly creates and depends on CONCRETE
 *    implementations:
 *    - MySQLReturnRepository (what if we switch to Cassandra?)
 *    - EmailNotificationSender (what if we want to add SMS or push notifications?)
 *    - FixedRateRefundCalculator (what if refund rules change by channel?)
 *
 * ❌ To change the database from MySQL to Cassandra, we MUST modify ReturnService.
 *
 * ❌ To switch from Email to SMS notifications, we MUST modify ReturnService.
 *
 * ❌ Unit testing ReturnService requires an actual MySQL database and email server!
 *    (No way to inject mocks)
 *
 * ❌ The high-level business logic is TIGHTLY COUPLED to low-level infrastructure.
 * ============================================================================
 */
public class ReturnService {

    // ❌ Directly depending on CONCRETE classes — tightly coupled!
    private MySQLReturnRepository repository = new MySQLReturnRepository();
    private EmailNotificationSender notifier = new EmailNotificationSender();
    private FixedRateRefundCalculator calculator = new FixedRateRefundCalculator();

    public void processReturn(String orderId, String customerId, double itemPrice) {
        // Calculate refund
        double refundAmount = calculator.calculate(itemPrice);

        // Store in database
        repository.saveReturn(orderId, customerId, refundAmount);

        // Notify customer
        notifier.send(customerId, "Your return for order " + orderId +
                " has been processed. Refund: $" + refundAmount);

        System.out.println("Return processed for order: " + orderId);
    }

    public static void main(String[] args) {
        ReturnService service = new ReturnService();
        service.processReturn("WMT-ORD-99001", "CUST-4421", 149.99);

        // ❌ What if we want to test with an in-memory database? IMPOSSIBLE.
        // ❌ What if we want to use Kafka notifications instead? Must modify this class.
        // ❌ What if we want dynamic refund rules? Must modify this class.
    }
}
