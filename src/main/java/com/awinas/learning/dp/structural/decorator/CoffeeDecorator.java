package com.awinas.learning.dp.structural.decorator;

/**
 * DECORATOR PATTERN - Abstract Decorator
 *
 * Wraps a Coffee object and delegates to it.
 * All concrete decorators extend this.
 *
 * Key: it HOLDS a Coffee (HAS-A) and also IS a Coffee (implements Coffee).
 * This is what makes chaining possible:
 *   new Sugar(new Milk(new SimpleCoffee()))
 */
public abstract class CoffeeDecorator implements Coffee {

    protected final Coffee coffee;   // the wrapped object

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription();   // delegate to wrapped coffee
    }

    @Override
    public double getCost() {
        return coffee.getCost();          // delegate to wrapped coffee
    }
}
