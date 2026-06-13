package com.awinas.learning.dp.creational.abstractfactory;

/**
 * Demo: The storefront renders themed components based on the active sale event.
 *
 * Key point: The client (this class) only talks to:
 *   1. ThemeFactoryProducer — to get the right factory
 *   2. SaleThemeFactory (interface) — to create products
 *   3. Banner, OfferCard, NotificationTemplate (interfaces) — to use products
 *
 * It NEVER references DiwaliSaleBanner, BlackFridayOfferCard, etc. directly.
 */
public class AbstractFactoryDemo {

    public static void main(String[] args) {

        System.out.println("=== Storefront: DIWALI Sale ===\n");
        renderStorefront("DIWALI", "Awinas");

        System.out.println("\n=== Storefront: BLACK FRIDAY Sale ===\n");
        renderStorefront("BLACK_FRIDAY", "Ravi");
    }

    /**
     * Client method that works entirely with abstractions.
     * Doesn't know or care whether it's Diwali or Black Friday.
     */
    private static void renderStorefront(String saleEvent, String customerName) {
        SaleThemeFactory factory = ThemeFactoryProducer.getFactory(saleEvent);

        Banner banner = factory.createBanner();
        OfferCard offerCard = factory.createOfferCard();
        NotificationTemplate notification = factory.createNotification();

        banner.display();
        offerCard.showOffer();
        notification.sendNotification(customerName);
    }
}
