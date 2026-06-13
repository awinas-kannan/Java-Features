package com.awinas.learning.dp.creational.abstractfactory;

/**
 * Concrete Factory: Creates Black Friday-themed components.
 */
public class BlackFridaySaleThemeFactory implements SaleThemeFactory {

    @Override
    public Banner createBanner() {
        return new BlackFridayBanner();
    }

    @Override
    public OfferCard createOfferCard() {
        return new BlackFridayOfferCard();
    }

    @Override
    public NotificationTemplate createNotification() {
        return new BlackFridayNotification();
    }
}
