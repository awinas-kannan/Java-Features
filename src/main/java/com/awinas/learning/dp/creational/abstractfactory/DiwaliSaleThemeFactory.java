package com.awinas.learning.dp.creational.abstractfactory;

/**
 * Concrete Factory: Creates Diwali-themed components.
 */
public class DiwaliSaleThemeFactory implements SaleThemeFactory {

    @Override
    public Banner createBanner() {
        return new DiwaliSaleBanner();
    }

    @Override
    public OfferCard createOfferCard() {
        return new DiwaliOfferCard();
    }

    @Override
    public NotificationTemplate createNotification() {
        return new DiwaliNotification();
    }
}
