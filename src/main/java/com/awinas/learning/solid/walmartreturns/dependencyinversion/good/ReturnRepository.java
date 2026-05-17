package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

/*
 * ============================================================================
 * DEPENDENCY INVERSION PRINCIPLE (DIP) - GOOD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "High-level modules should NOT depend on low-level modules.
 *    Both should depend on ABSTRACTIONS."
 *
 * ============================================================================
 * GOOD DESIGN — Depend on Abstractions, not Concretions:
 * ============================================================================
 *
 * High-level module (ReturnService) depends on INTERFACES:
 *   - ReturnRepository      (abstraction for data storage)
 *   - NotificationSender    (abstraction for customer notifications)
 *   - RefundCalculator      (abstraction for refund calculation)
 *
 * Low-level modules IMPLEMENT these interfaces:
 *   - MySQLReturnRepository / CassandraReturnRepository
 *   - EmailNotificationSender / SMSNotificationSender / KafkaNotificationSender
 *   - FixedRateRefundCalculator / DynamicRefundCalculator
 *
 * Dependencies are INJECTED via constructor (Constructor Injection).
 *
 * ============================================================================
 * WHY IS THIS GOOD?
 * ============================================================================
 *
 * ✅ ReturnService never changes when we swap MySQL for Cassandra.
 * ✅ ReturnService never changes when we switch from Email to SMS/Kafka.
 * ✅ ReturnService never changes when refund calculation rules evolve.
 * ✅ Easy to unit test — inject mocks via constructor.
 * ✅ Follows the "Depend on abstractions, not concretions" principle.
 * ✅ Decoupled — high-level business logic is independent of infrastructure.
 * ============================================================================
 */
public interface ReturnRepository {
    void saveReturn(String orderId, String customerId, double refundAmount);
}
