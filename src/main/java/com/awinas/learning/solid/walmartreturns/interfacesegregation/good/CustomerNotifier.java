package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

public interface CustomerNotifier {
    void sendCustomerNotification(String customerId, String message);
}
