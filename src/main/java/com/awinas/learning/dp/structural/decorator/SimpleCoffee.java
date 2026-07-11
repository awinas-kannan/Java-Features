package com.awinas.learning.dp.structural.decorator;

/**
 * DECORATOR PATTERN - Concrete Component
 *
 * The base object being decorated.
 * Has no extras — just plain coffee.
 */
public class SimpleCoffee implements Coffee {

    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return 50.0;
    }
}
