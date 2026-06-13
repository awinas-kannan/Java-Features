package com.awinas.learning.dp.creational.abstractfactory;

/**
 * Abstract Factory: Creates a family of themed UI components for sales events.
 */
public interface SaleThemeFactory {
    Banner createBanner();
    OfferCard createOfferCard();
    NotificationTemplate createNotification();
}
