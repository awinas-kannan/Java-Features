package com.awinas.learning.dp.structural.decorator;

/**
 * DECORATOR PATTERN - Concrete Decorator (Milk)
 *
 * Adds milk on top of whatever coffee is passed in.
 * Overrides getDescription() and getCost() to ADD its own contribution,
 * then delegates the rest to the wrapped coffee.
 */
public class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + " + Milk";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 15.0;
    }
}
