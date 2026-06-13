package com.awinas.learning.dp.creational.abstractfactory;

public class DiwaliNotification implements NotificationTemplate {
    @Override
    public void sendNotification(String customerName) {
        System.out.println("[Diwali Push] Hey " + customerName + "! Diwali deals are LIVE. Shop now & light up your savings!");
    }
}
