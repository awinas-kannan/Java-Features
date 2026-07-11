package com.awinas.learning.dp.behavioural.observer;

import java.util.ArrayList;
import java.util.List;

// Subject - holds the list of observers and notifies them on every status change
// Order doesn't know what EmailService or SMSService does — it just broadcasts
public class Order {

    private final String orderId;
    private OrderStatus status;
    private final List<OrderObserver> observers = new ArrayList<>();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    // When status changes — broadcast to all registered observers
    public void setStatus(OrderStatus newStatus) {
        System.out.println("\n[Order] " + orderId + " status changed: "
                + this.status + " -> " + newStatus);
        this.status = newStatus;
        notifyObservers();
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(orderId, status);
        }
    }

    public String getOrderId()    { return orderId; }
    public OrderStatus getStatus(){ return status; }
}
