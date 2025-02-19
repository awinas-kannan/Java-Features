package com.awinas.learning.solid.liskov.good;
class UPIPayment extends Payment {
    @Override
    void processPayment(double amount) {
        System.out.println("Processing UPI payment of $" + amount);
    }
}