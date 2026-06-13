# Creational Design Patterns - Study Guide

## Overview

Creational patterns deal with **object creation mechanisms** — creating objects in a manner suitable to the situation rather than using basic instantiation directly.

---

## Quick Reference

| # | Pattern | Description | Retail Example |
|---|---------|-------------|----------------|
| 1 | **Singleton** | Ensures a class has only one instance and provides a global point of access to it. | A single `InventoryManager` instance shared across warehouse and order services. |
| 2 | **Factory Method** | Defines an interface for creating objects, but lets the factory decide which class to instantiate. | `PaymentProcessorFactory` creates CreditCard / UPI / Wallet / COD processors based on customer choice. |
| 3 | **Abstract Factory** | Provides an interface for creating **families of related objects** without specifying concrete classes. | `SaleThemeFactory` creates matching Banner + OfferCard + Notification for Diwali or Black Friday. |
| 4 | **Builder** | Separates the construction of a complex object from its representation, allowing step-by-step creation. | Fluent `Order.builder()` to construct orders with items, address, gift wrap, coupon, delivery options. |
| 5 | **Prototype** | Creates new objects by **cloning** an existing object rather than building from scratch. | Clone a base `ProductListing` to quickly create size/colour variants without re-entering all details. |

---

## 1. Singleton Pattern

### What it does (Simple Terms)

> "Only ONE manager in the entire store. Everyone talks to the SAME person."

Imagine a retail warehouse has one single Inventory Manager. Whether the order team or the warehouse team asks about stock — they all talk to the same person. There's no confusion, no duplicate records.

### When to use

- Database connection pools
- Configuration managers
- Logging services
- Cache managers

### Class Diagram

```
┌─────────────────────────────────────────────┐
│           InventoryManager                   │
├─────────────────────────────────────────────┤
│ - instance: InventoryManager [static]        │
│ - totalProducts: int                         │
├─────────────────────────────────────────────┤
│ - InventoryManager() [private constructor]   │
│ + getInstance(): InventoryManager [static]   │
│ + addStock(name, qty): void                  │
│ + removeStock(name, qty): void               │
│ + getTotalProducts(): int                    │
└─────────────────────────────────────────────┘
         ▲                    ▲
         │                    │
         │ uses same          │ uses same
         │ instance           │ instance
         │                    │
┌────────────────┐   ┌────────────────────┐
│ WarehouseService│   │   OrderService     │
└────────────────┘   └────────────────────┘
```

### Key Points

- Private constructor — no one can do `new InventoryManager()`
- Static `getInstance()` — returns the single instance
- Double-checked locking — thread-safe lazy initialization
- In Spring: `@Service` / `@Component` are singletons by default (container-managed)

---

## 2. Factory Method Pattern

### What it does (Simple Terms)

> "Tell the cashier HOW you want to pay. The cashier picks the right machine — you don't care which."

At checkout, the customer says "UPI" or "Credit Card". The factory creates the right payment processor. The client code doesn't know or care about `CreditCardProcessor` vs `UPIProcessor` — it just calls `processPayment()`.

### When to use

- When the exact type of object to create is determined at runtime
- When you want to decouple object creation from usage
- When adding new types shouldn't require changing existing code

### Class Diagram

```
┌──────────────────────────────────────┐
│       PaymentProcessorFactory        │
├──────────────────────────────────────┤
│ + createProcessor(type): PaymentProcessor [static] │
└──────────────────────────────────────┘
                    │
                    │ creates
                    ▼
┌──────────────────────────────────────┐
│     <<interface>> PaymentProcessor   │
├──────────────────────────────────────┤
│ + processPayment(amount): void       │
│ + getPaymentMethod(): String         │
└──────────────────────────────────────┘
          ▲         ▲         ▲         ▲
          │         │         │         │
          │         │         │         │
┌─────────┴──┐ ┌───┴─────┐ ┌┴────────┐ ┌┴───────────────┐
│CreditCard  │ │  UPI    │ │ Wallet  │ │CashOnDelivery  │
│Processor   │ │Processor│ │Processor│ │Processor       │
└────────────┘ └─────────┘ └─────────┘ └────────────────┘
```

### How it flows

```
Client                Factory                    Product
  │                      │                          │
  │── "UPI" ────────────►│                          │
  │                      │── new UPIProcessor() ───►│
  │◄── PaymentProcessor ─┤                          │
  │                      │                          │
  │── processPayment() ─────────────────────────────►
```

---

## 3. Abstract Factory Pattern

### What it does (Simple Terms)

> "It's Diwali? Okay — ALL decorations, ALL banners, ALL notifications will be Diwali-themed. One decision, entire family changes."

Unlike Factory Method (which creates ONE product), Abstract Factory creates a **family of related products** that go together. Switch from Diwali to Black Friday and the entire look — banner, offer card, notification — changes in one shot.

### When to use

- When you need to create families of related objects
- When the system should be independent of how products are created
- When products from one family shouldn't be mixed with another

### Class Diagram

```
┌─────────────────────────┐
│   ThemeFactoryProducer   │
├─────────────────────────┤
│ + getFactory(event):     │
│   SaleThemeFactory       │
└────────────┬────────────┘
             │ returns
             ▼
┌──────────────────────────────────────────┐
│    <<interface>> SaleThemeFactory         │
├──────────────────────────────────────────┤
│ + createBanner(): Banner                  │
│ + createOfferCard(): OfferCard            │
│ + createNotification(): NotificationTemplate │
└──────────────────────────────────────────┘
             ▲                      ▲
             │                      │
┌────────────┴──────────┐  ┌───────┴────────────────┐
│ DiwaliSaleThemeFactory │  │ BlackFridaySaleThemeFactory │
├───────────────────────┤  ├────────────────────────┤
│ createBanner()        │  │ createBanner()          │
│ createOfferCard()     │  │ createOfferCard()       │
│ createNotification()  │  │ createNotification()    │
└───────────┬───────────┘  └──────────┬─────────────┘
            │                          │
            │ creates                  │ creates
            ▼                          ▼
┌─────────────────────┐     ┌──────────────────────────┐
│ DiwaliSaleBanner     │     │ BlackFridayBanner         │
│ DiwaliOfferCard      │     │ BlackFridayOfferCard      │
│ DiwaliNotification   │     │ BlackFridayNotification   │
└─────────────────────┘     └──────────────────────────┘


Abstract Products (Interfaces):
┌────────────────┐  ┌────────────────┐  ┌──────────────────────┐
│ <<interface>>  │  │ <<interface>>  │  │ <<interface>>         │
│    Banner      │  │   OfferCard   │  │ NotificationTemplate  │
├────────────────┤  ├────────────────┤  ├──────────────────────┤
│ + display()    │  │ + showOffer() │  │ + sendNotification() │
└────────────────┘  └────────────────┘  └──────────────────────┘
```

### Factory Method vs Abstract Factory

| Factory Method | Abstract Factory |
|---------------|-----------------|
| Creates **one** product | Creates a **family** of related products |
| One factory method | Multiple factory methods in one interface |
| `PaymentProcessorFactory → PaymentProcessor` | `SaleThemeFactory → Banner + OfferCard + Notification` |

---

## 4. Builder Pattern

### What it does (Simple Terms)

> "Building a custom pizza — you pick the base, then add toppings one by one. You don't have to decide everything upfront."

A retail Order has many optional fields: gift wrap, coupon, express delivery, special instructions. Instead of a constructor with 10 parameters, you build it step-by-step and call `.build()` when done.

### When to use

- Object has many optional parameters
- You want to avoid telescoping constructors (`new Order(a, b, null, null, true, null, false)`)
- Object construction has multiple steps
- You want immutable objects with flexible construction

### Class Diagram

```
┌───────────────────────────────────────────────────┐
│                     Order                          │
├───────────────────────────────────────────────────┤
│ - orderId: String [final]                         │
│ - customerName: String [final]                    │
│ - items: List<String> [final]                     │
│ - shippingAddress: String [final]                 │
│ - paymentMethod: String [final]                   │
│ - giftWrap: boolean [final]                       │
│ - couponCode: String [final]                      │
│ - expressDelivery: boolean [final]                │
│ - specialInstructions: String [final]             │
├───────────────────────────────────────────────────┤
│ - Order(Builder) [private constructor]            │
│ + builder(): Builder [static]                     │
│ + builder(orderId, customerName): Builder [static]│
│ + toString(): String                              │
└───────────────────────────────────────────────────┘
                        │
                        │ contains (inner class)
                        ▼
┌───────────────────────────────────────────────────┐
│               Order.Builder                        │
├───────────────────────────────────────────────────┤
│ - orderId: String                                 │
│ - customerName: String                            │
│ - items: List<String>                             │
│ - shippingAddress: String                         │
│ - paymentMethod: String = "COD"                   │
│ - giftWrap: boolean = false                       │
│ - couponCode: String                              │
│ - expressDelivery: boolean = false                │
│ - specialInstructions: String                     │
├───────────────────────────────────────────────────┤
│ + orderId(id): Builder                            │
│ + customerName(name): Builder                     │
│ + addItem(item): Builder                          │
│ + shippingAddress(addr): Builder                  │
│ + paymentMethod(method): Builder                  │
│ + giftWrap(flag): Builder                         │
│ + couponCode(code): Builder                       │
│ + expressDelivery(flag): Builder                  │
│ + specialInstructions(text): Builder              │
│ + build(): Order  [validates & creates]           │
└───────────────────────────────────────────────────┘
```

### How it flows

```
Client
  │
  │── Order.builder() ──────────────────────► Builder (empty)
  │── .orderId("ORD-1001") ────────────────► Builder (sets orderId)
  │── .customerName("Awinas") ─────────────► Builder (sets name)
  │── .addItem("Samsung Galaxy") ──────────► Builder (adds item)
  │── .shippingAddress("Bangalore") ───────► Builder (sets address)
  │── .giftWrap(true) ────────────────────► Builder (sets flag)
  │── .build() ────────────────────────────► validates → new Order(this)
  │◄── Order (immutable) ──────────────────┤
```

---

## 5. Prototype Pattern

### What it does (Simple Terms)

> "Photocopy the form, then just change the name. Don't fill the entire form again from scratch."

A seller has a T-shirt listing with brand, category, description, price all filled in. To create a size "L" variant, just clone the base listing and change the size. No need to re-enter 20 fields.

### When to use

- Creating an object is expensive (DB calls, API calls, heavy computation)
- Objects differ only in a few fields (variants)
- You want to avoid subclassing just to configure different initial states

### Class Diagram

```
┌──────────────────────────────────────────────┐
│         <<interface>> Cloneable              │
└──────────────────────────────────────────────┘
                     ▲
                     │ implements
                     │
┌──────────────────────────────────────────────┐
│              ProductListing                    │
├──────────────────────────────────────────────┤
│ - productId: String                           │
│ - name: String                                │
│ - brand: String                               │
│ - category: String                            │
│ - basePrice: double                           │
│ - size: String                                │
│ - colour: String                              │
│ - description: String                         │
├──────────────────────────────────────────────┤
│ + ProductListing(id, name, brand,             │
│       category, price, desc)                  │
│ + clone(): ProductListing                     │
│ + setProductId(id): void                      │
│ + setSize(size): void                         │
│ + setColour(colour): void                     │
│ + setBasePrice(price): void                   │
└──────────────────────────────────────────────┘
```

### How it flows

```
Base Listing (fully configured)
  │
  │── clone() ──► Variant 1 (change size to "S", new SKU)
  │── clone() ──► Variant 2 (change size to "L", new SKU)
  │── clone() ──► Variant 3 (change colour to "White", new SKU)
  │── clone() ──► Variant 4 (change size to "XL", adjust price, new SKU)
  │
  ▼
Original stays UNCHANGED
```

### Shallow vs Deep Clone

| Type | What happens | When to use |
|------|-------------|-------------|
| Shallow Clone | Copies primitives, shares object references | Simple objects with no nested mutable objects |
| Deep Clone | Copies everything including nested objects | Objects containing Lists, Maps, or other mutable references |

---

## Summary: When to Use Which?

| Scenario | Pattern |
|----------|---------|
| Need exactly ONE shared instance | **Singleton** |
| Need to create ONE object, type decided at runtime | **Factory Method** |
| Need to create a FAMILY of related objects together | **Abstract Factory** |
| Object has many optional fields, built step-by-step | **Builder** |
| Need copies of an existing object with minor changes | **Prototype** |

---

## Folder Structure

```
creational/
├── singletondp/v2/
│   ├── InventoryManager.java
│   └── SingletonDemo.java
├── factorymethod/
│   ├── PaymentProcessor.java (interface)
│   ├── CreditCardProcessor.java
│   ├── UPIProcessor.java
│   ├── WalletProcessor.java
│   ├── CashOnDeliveryProcessor.java
│   ├── PaymentProcessorFactory.java
│   └── FactoryMethodDemo.java
├── abstractfactory/
│   ├── Banner.java (interface)
│   ├── OfferCard.java (interface)
│   ├── NotificationTemplate.java (interface)
│   ├── SaleThemeFactory.java (abstract factory interface)
│   ├── ThemeFactoryProducer.java (factory producer)
│   ├── DiwaliSaleThemeFactory.java
│   ├── DiwaliSaleBanner.java
│   ├── DiwaliOfferCard.java
│   ├── DiwaliNotification.java
│   ├── BlackFridaySaleThemeFactory.java
│   ├── BlackFridayBanner.java
│   ├── BlackFridayOfferCard.java
│   ├── BlackFridayNotification.java
│   └── AbstractFactoryDemo.java
├── builder/
│   ├── Order.java (with inner Builder class)
│   └── BuilderDemo.java
└── prototype/
    ├── ProductListing.java (implements Cloneable)
    └── PrototypeDemo.java
```
