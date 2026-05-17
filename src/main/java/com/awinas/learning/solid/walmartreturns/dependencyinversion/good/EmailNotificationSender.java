package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

public class EmailNotificationSender implements NotificationSender {

    @Override
    public void send(String customerId, String message) {
        System.out.println("[EMAIL] To: " + customerId + " | " + message);
    }
}
