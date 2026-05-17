package com.awinas.learning.solid.walmartreturns.dependencyinversion.bad;

public class EmailNotificationSender {

    public void send(String customerId, String message) {
        System.out.println("[EMAIL] To: " + customerId + " | Message: " + message);
    }
}
