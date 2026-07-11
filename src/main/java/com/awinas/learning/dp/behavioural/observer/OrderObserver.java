package com.awinas.learning.dp.behavioural.observer;

// Observer interface
// Every service that wants to react to order events implements this
public interface OrderObserver {
    void update(String orderId, OrderStatus status);
}
