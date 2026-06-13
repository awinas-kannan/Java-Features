package com.awinas.learning.dp.creational.abstractfactory;

/**
 * Factory Producer: Returns the correct concrete factory
 * based on the sale event. This separates factory selection
 * from client code (AbstractFactoryDemo doesn't need to know
 * which concrete factories exist).
 */
public class ThemeFactoryProducer {

    public static SaleThemeFactory getFactory(String saleEvent) {
        switch (saleEvent.toUpperCase()) {
            case "DIWALI":
                return new DiwaliSaleThemeFactory();
            case "BLACK_FRIDAY":
                return new BlackFridaySaleThemeFactory();
            default:
                throw new IllegalArgumentException("No theme configured for: " + saleEvent);
        }
    }
}
