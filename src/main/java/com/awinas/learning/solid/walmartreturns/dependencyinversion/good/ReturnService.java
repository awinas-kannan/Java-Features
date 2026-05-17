package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

/**
 * ✅ HIGH-LEVEL MODULE — depends ONLY on abstractions (interfaces).
 *
 * ReturnService does NOT know:
 *   - Whether data goes to MySQL, Cassandra, or in-memory (ReturnRepository)
 *   - Whether notifications go via Email, SMS, or Kafka (NotificationSender)
 *   - How refunds are calculated — fixed or dynamic (RefundCalculator)
 *
 * Dependencies are INJECTED through the constructor (Constructor Injection / DI).
 * This is exactly what Spring Boot's @Autowired does under the hood.
 */
public class ReturnService {

    private final ReturnRepository repository;
    private final NotificationSender notifier;
    private final RefundCalculator calculator;

    // Constructor Injection — dependencies injected from outside
    public ReturnService(ReturnRepository repository,
                         NotificationSender notifier,
                         RefundCalculator calculator) {
        this.repository = repository;
        this.notifier = notifier;
        this.calculator = calculator;
    }

    public void processReturn(String orderId, String customerId,
                              double itemPrice, int daysSincePurchase) {
        double refundAmount = calculator.calculate(itemPrice, daysSincePurchase);
        repository.saveReturn(orderId, customerId, refundAmount);
        notifier.send(customerId, "Your return for order " + orderId +
                " has been processed. Refund: $" + String.format("%.2f", refundAmount));

        System.out.println("✅ Return processed successfully for order: " + orderId);
    }

    public static void main(String[] args) {
        System.out.println("=== Configuration 1: MySQL + Email + Dynamic Calculator ===\n");
        ReturnService service1 = new ReturnService(
                new MySQLReturnRepository(),
                new EmailNotificationSender(),
                new DynamicRefundCalculator()
        );
        service1.processReturn("WMT-ORD-99001", "CUST-4421", 149.99, 10);

        System.out.println("\n=== Configuration 2: Cassandra + Kafka + Dynamic Calculator ===\n");
        ReturnService service2 = new ReturnService(
                new CassandraReturnRepository(),
                new KafkaNotificationSender(),
                new DynamicRefundCalculator()
        );
        service2.processReturn("WMT-ORD-99002", "CUST-8832", 299.99, 45);

        // ✅ ReturnService code NEVER changed — we just passed different implementations!
        // ✅ For unit tests, inject mocks:
        //    new ReturnService(mockRepo, mockNotifier, mockCalculator)
    }
}
