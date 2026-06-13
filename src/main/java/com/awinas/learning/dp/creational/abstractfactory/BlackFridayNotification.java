package com.awinas.learning.dp.creational.abstractfactory;

public class BlackFridayNotification implements NotificationTemplate {
    @Override
    public void sendNotification(String customerName) {
        System.out.println("[BF Alert] " + customerName + ", Black Friday deals are going FAST. Grab yours before midnight!");
    }
}
