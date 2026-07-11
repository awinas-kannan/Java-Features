package com.awinas.learning.dp.structural.decorator;

/**
 * DECORATOR PATTERN - Component Interface
 *
 * Both the base coffee and every decorator implement this.
 * This is what allows decorators to wrap each other endlessly.
 */
public interface Coffee {
    String getDescription();
    double getCost();
}
