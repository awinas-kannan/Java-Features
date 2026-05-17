package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

public interface NotificationSender {
    void send(String customerId, String message);
}
