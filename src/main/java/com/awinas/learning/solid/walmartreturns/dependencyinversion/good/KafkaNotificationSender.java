package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

/**
 * ✅ Swap-in Kafka event-based notifications without touching ReturnService!
 */
public class KafkaNotificationSender implements NotificationSender {

    @Override
    public void send(String customerId, String message) {
        System.out.println("[KAFKA EVENT] Topic: returns.notifications | Customer: " +
                customerId + " | " + message);
    }
}
