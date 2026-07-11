package com.awinas.learning.dp.structural.decorator;

public class Main {

    public static void main(String[] args) {

        // ---- Plain coffee ----
        Coffee coffee = new SimpleCoffee();
        print(coffee);

        // ---- Coffee + Milk ----
        Coffee coffeeWithMilk = new MilkDecorator(new SimpleCoffee());
        print(coffeeWithMilk);

        // ---- Coffee + Milk + Sugar ----
        Coffee coffeeWithMilkAndSugar = new SugarDecorator(
                                            new MilkDecorator(
                                                new SimpleCoffee()));
        print(coffeeWithMilkAndSugar);

        // ---- Coffee + Milk + Sugar + Whip ----
        Coffee fancyCoffee = new WhipDecorator(
                                 new SugarDecorator(
                                     new MilkDecorator(
                                         new SimpleCoffee())));
        print(fancyCoffee);

        // ---- Double Sugar ----
        // You can apply the same decorator multiple times
        Coffee doubleSugar = new SugarDecorator(
                                 new SugarDecorator(
                                     new SimpleCoffee()));
        print(doubleSugar);
    }

    private static void print(Coffee coffee) {
        System.out.println(coffee.getDescription() + "  -->  Rs." + coffee.getCost());
    }
}
