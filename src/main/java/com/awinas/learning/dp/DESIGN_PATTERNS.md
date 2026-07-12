# Design Patterns — Master Reference

## What is a Design Pattern?

A design pattern is a **reusable solution to a commonly occurring problem** in software design.

It is not code — it is a **blueprint**. You apply it to your specific problem.

Introduced by the **Gang of Four (GoF)** in 1994.
They documented **23 patterns** across 3 categories.

### Why use Design Patterns?

| Without Patterns | With Patterns |
|-----------------|---------------|
| Code tightly coupled | Code loosely coupled |
| Hard to extend | Add new class, don't modify existing |
| Logic scattered everywhere | Concerns clearly separated |
| Difficult to test | Easy to mock and test |
| Hard to explain to team | Common vocabulary — "use an Adapter here" |

---

## Three Categories

```
Design Patterns
      |
      ├── Creational  → HOW objects are created
      |
      ├── Structural  → HOW objects are composed and connected
      |
      └── Behavioural → HOW objects communicate and share responsibility
```

---

## Creational Patterns (5)

> Control object creation — decouple creation logic from business logic.

| # | Pattern | Interview Description | Example Scenario | Block Diagram |
|---|---------|----------------------|-----------------|---------------|
| 1 | **Singleton** | Only ONE instance exists across the entire application. All callers share the same object. | `BillPughSingleton` — same instance returned every time via inner static helper class | `getInstance()` → `SingletonHelper.INSTANCE` → same object always |
| 2 | **Factory Method** | Creates objects without exposing the creation logic. Caller asks by type name — factory decides which class to return. | `PaymentProcessorFactory` — returns `UPIProcessor`, `CreditCardProcessor`, `WalletProcessor`, `CashOnDeliveryProcessor` by payment type string | `create("UPI")` → `UPIProcessor` \| `create("CARD")` → `CreditCardProcessor` |
| 3 | **Builder** | Constructs a complex object step by step. Avoids long constructors with many optional fields. Each step is optional and the object is built at the end. | `Order.Builder` — orderId + items + shipping + coupon + giftWrap + expressDelivery are all set step by step | `Order.builder("ORD-1").items(...).shipping(...).coupon("10OFF").giftWrap(true).build()` |
| 4 | **Abstract Factory** | Creates a **family of related objects** together. Guarantees all objects belong to the same provider — you cannot accidentally mix objects from different families. | `DiwaliSaleThemeFactory` creates matched `Banner` + `OfferCard` + `Notification`. `BlackFridaySaleThemeFactory` creates a different matched set. Both implement `SaleThemeFactory`. | `getFactory("DIWALI")` → `DiwaliSaleThemeFactory` → `DiwaliSaleBanner` + `DiwaliOfferCard` + `DiwaliNotification` |
| 5 | **Prototype** | Creates new objects by **cloning** an existing object. Avoids expensive creation — copy instead of build from scratch. | `ProductListing.clone()` — clone an existing product listing and change only the fields that differ | `existingProduct.clone()` → new copy with same defaults |

---

## Structural Patterns (7)

> Control how objects are composed and connected to form larger structures.

| # | Pattern | Interview Description | Example Scenario | Block Diagram |
|---|---------|----------------------|-----------------|---------------|
| 6 | **Adapter** | Makes two incompatible interfaces work together without modifying either side. Wraps a third-party class to match your interface. Think: power socket adapter. | `RazorpayAdapter`, `StripeAdapter`, `PaypalAdapter` — each wraps its SDK behind the common `PaymentService` interface. `CheckoutService` calls `pay()` on all three the same way. | `CheckoutService` → `PaymentService` → `RazorpayAdapter` → `razorpaySDK.razorpayPay()` |
| 7 | **Decorator** | Adds new behaviour to an existing object at runtime by wrapping it. Same interface, infinitely stackable. Does NOT change the original class. | `SimpleCoffee` wrapped with `MilkDecorator` → `SugarDecorator` → `WhipDecorator`. Each layer adds to description and cost. | `new Whip(new Sugar(new Milk(new SimpleCoffee())))` → Rs.90 |
| 8 | **Proxy** | Provides a surrogate that controls access to the real object. Same interface — caller never knows a proxy is involved. Acts as a validation, logging, or caching layer. | `PaymentGatewayProxy` — enforces Rs.5,000 single-txn limit and Rs.10,000 daily limit. Logs every request. Only delegates to `RealPaymentGateway` if all checks pass. | `Caller` → `PaymentGatewayProxy` (limit check + log) → `RealPaymentGateway` |
| 9 | **Facade** | Provides a simplified interface over a complex subsystem. Hides internal complexity behind one clean entry point. | `OrderFacade` — `placeOrder()` internally coordinates `InventoryService` → `PaymentService` → `ShippingService` → `NotificationService`. Client calls one method. | `client.placeOrder()` → Facade → checkStock + processPayment + createShipment + sendEmail |
| 10 | **Bridge** | Decouples abstraction from implementation so both can vary independently. Avoids class explosion when you have multiple dimensions. | — | — |
| 11 | **Composite** | Composes objects into tree structures. Treats individual objects and groups of objects uniformly. | — | — |
| 12 | **Flyweight** | Shares common state among many objects to save memory. Objects split into intrinsic (shared) and extrinsic (unique) state. | — | — |

---

## Behavioural Patterns (11)

> Control how objects communicate and share responsibility.

| # | Pattern | Interview Description | Example Scenario | Block Diagram |
|---|---------|----------------------|-----------------|---------------|
| 13 | **Strategy** | Defines a family of algorithms, encapsulates each, and makes them swappable at runtime. Context delegates — no if/else needed. | `CheckoutService` delegates to `UpiStrategy`, `CardStrategy`, or `NetBankingStrategy`. Each strategy owns its own `pay()` logic. Swap strategy at runtime — checkout never changes. | `CheckoutService.pay()` → `strategy.pay(amount)` → UPI / Card / NetBanking logic |
| 14 | **Observer** | One-to-many dependency. When Subject changes state, all registered Observers are notified automatically. Subject and Observers are loosely coupled. | `Order` (Subject) broadcasts every status change. `EmailService`, `SMSService`, `WarehouseService`, `LoyaltyService` each react differently. `Order` never imports any of them. | `order.setStatus(SHIPPED)` → notifyAll → Email + SMS + Warehouse |
| 15 | **Command** | Encapsulates a request as an object. Supports queuing, logging, and undo operations. Decouples sender from receiver. | — | — |
| 16 | **Chain of Responsibility** | Passes a request along a chain of handlers. Each handler processes it or passes it on. Avoids coupling sender to a specific receiver. | — | — |
| 17 | **Iterator** | Provides a uniform way to traverse a collection without exposing its internal structure. | — | — |
| 18 | **Mediator** | Defines a central object that handles communication between other objects. Reduces direct dependencies. | — | — |
| 19 | **Memento** | Captures an object's state so it can be restored later — without violating encapsulation. Supports undo/rollback. | — | — |
| 20 | **Interpreter** | Defines a grammar for a language and an interpreter to parse it. Used for expression parsing or DSLs. | — | — |
| 21 | **Template Method** | Defines the skeleton of an algorithm in a base class. Subclasses override specific steps without changing the structure. | `OrderProcessor` — `processOrder()` is the fixed skeleton. `StandardOrderProcessor`, `PremiumOrderProcessor`, `InternationalOrderProcessor` each override `calculatePrice()` + `processPayment()`. Premium also overrides the `applyDiscount()` hook. | `processOrder()` → validate → `calculatePrice()` (abstract) → `applyDiscount()` (hook) → `processPayment()` (abstract) → confirm |
| 22 | **State** | Allows an object to change its behaviour when its internal state changes. Replaces large if/else or switch blocks on state. | — | — |
| 23 | **Visitor** | Lets you add new operations to an object structure without modifying the classes. | — | — |

---

## Patterns Covered — Detailed Diagrams

### 1. Singleton

```
Caller A ──┐
           ├──► getInstance() ──► SingletonHelper.INSTANCE (created once)
Caller B ──┘                              │
                                          └──► same object returned always
```

---

### 2. Factory Method

```
PaymentProcessorFactory.create("UPI")       ──► UPIProcessor
PaymentProcessorFactory.create("CARD")      ──► CreditCardProcessor
PaymentProcessorFactory.create("WALLET")    ──► WalletProcessor
PaymentProcessorFactory.create("COD")       ──► CashOnDeliveryProcessor

Caller only calls create() — never imports the concrete classes
```

---

### 3. Builder

```
Order.Builder("ORD-001")
     .customerName("John")
     .addItem("iPhone")
     .shippingAddress("Chennai")
     .paymentMethod("UPI")
     .giftWrap(true)               ← optional
     .couponCode("SAVE10")         ← optional
     .expressDelivery(true)        ← optional
     .build()
          │
          └──► Order (fully constructed, immutable)
```

---

### 4. Abstract Factory

```
SaleThemeFactory (interface)
      │
      ├── createBanner()
      ├── createOfferCard()
      └── createNotification()
           │
           ├── DiwaliSaleThemeFactory
           │       ├── DiwaliSaleBanner       ("Shop the Diwali Sale!")
           │       ├── DiwaliOfferCard        ("20% off on electronics")
           │       └── DiwaliNotification     ("🪔 Diwali Offers Live!")
           │
           └── BlackFridaySaleThemeFactory
                   ├── BlackFridayBanner      ("Black Friday Deals!")
                   ├── BlackFridayOfferCard   ("50% off sitewide")
                   └── BlackFridayNotification ("🖤 Black Friday is here!")

Key guarantee: DiwaliSaleThemeFactory never returns a BlackFriday object.
The entire set is always from the same theme family.
```

---

### 5. Prototype

```
ProductListing original = new ProductListing("iPhone", 79999, "Electronics")
          │
          └──► original.clone()
                    │
                    └──► ProductListing copy (same defaults, modify only what differs)
```

---

### 6. Adapter

```
CheckoutService
      │
      │ calls pay(amount, method) — our interface
      │
 PaymentService (interface)
      │
 ┌────┼────────┐
 │    │        │
RZP  STR     PPL
Adapter      Adapter      Adapter
 │    │        │
 │    │        └──► paypalSDK.executePayment()
 │    └──────────── stripeSDK.stripeCharge()
 └─────────────── razorpaySDK.razorpayPay()

SDK method names are all different — Adapters hide that
```

---

### 7. Decorator

```
new WhipDecorator(
    new SugarDecorator(
        new MilkDecorator(
            new SimpleCoffee()
        )))

getCost() call chain (inside out):
  SimpleCoffee  →  Rs. 50
  + Milk        →  Rs. 65
  + Sugar       →  Rs. 70
  + Whip        →  Rs. 90

getDescription():  Simple Coffee + Milk + Sugar + Whip
```

---

### 8. Proxy

```
Caller
  │
  ▼
PaymentGatewayProxy  ←── same PaymentGateway interface
  │
  ├── Check: amount > Rs.5,000?  → BLOCK
  ├── Check: daily spend > Rs.10,000? → BLOCK
  ├── Log request with timestamp
  │
  │ all checks pass
  ▼
RealPaymentGateway  ←── actual payment processing
```

---

### 9. Facade

```
Client
  │
  │ placeOrder() / cancelOrder()   ← one simple call
  │
OrderFacade
  │
  ├── InventoryService   → checkStock() → reserveStock()
  ├── PaymentService     → processPayment() → returns txnId
  ├── ShippingService    → createShipment() → returns trackingId
  └── NotificationService → sendOrderConfirmation()

Client never imports or calls any subsystem directly.
Add LoyaltyService later? Update Facade only — client untouched.
```

---

### 13. Strategy

```
CheckoutService
  │
  │ delegates to:
  │
  ├── UpiStrategy.pay(amount)
  │      └── UPI ref id, UPI ID, Rs.499
  │
  ├── CardStrategy.pay(amount)
  │      └── Card auth, masked card, Rs.1299
  │
  └── NetBankingStrategy.pay(amount)
         └── Bank redirect, HDFC, Rs.850

Swap strategy at runtime — CheckoutService never changes
```

---

### 14. Observer

```
Order (Subject)
  │
  │ setStatus(SHIPPED) → notifyObservers()
  │
  ├── EmailService.update()      → "Your order has shipped!" email
  ├── SMSService.update()        → "Order on the way!" SMS
  ├── WarehouseService.update()  → "Hand to courier, update inventory"
  └── LoyaltyService.update()    → no action (only fires on DELIVERED)

Order never imports EmailService, SMSService, or any observer
```

---

### 21. Template Method

```
OrderProcessor  (Abstract Class)
  │
  └── processOrder()  ← FINAL — skeleton cannot change
            │
            ├── 1. validateOrder()     ← concrete  (same for all, defined in base)
            │
            ├── 2. calculatePrice()    ← abstract  (MUST override)
            │
            ├── 3. applyDiscount()     ← hook      (CAN override, default = no discount)
            │
            ├── 4. processPayment()    ← abstract  (MUST override)
            │
            └── 5. sendConfirmation()  ← concrete  (same for all, defined in base)

            │
            ├── StandardOrderProcessor
            │       calculatePrice()  → base + Rs.49 delivery
            │       processPayment()  → standard gateway
            │       applyDiscount()   → (not overridden — no discount)
            │
            ├── PremiumOrderProcessor
            │       calculatePrice()  → base, free delivery
            │       applyDiscount()   → 10% loyalty discount   ← overrides hook
            │       processPayment()  → priority queue
            │
            └── InternationalOrderProcessor
                    calculatePrice()  → base + Rs.499 shipping + 18% customs
                    processPayment()  → forex gateway
                    applyDiscount()   → (not overridden — no domestic discounts)
```

---

## Quick Interview Cheat Sheet

| Question | Answer |
|----------|--------|
| Adapter vs Decorator | Adapter changes the interface. Decorator keeps the same interface but adds behaviour. |
| Proxy vs Decorator | Proxy controls access (validation, limits, security). Decorator adds features. |
| Factory vs Abstract Factory | Factory creates one object. Abstract Factory creates a family of related objects. |
| Strategy vs Command | Strategy swaps the algorithm. Command encapsulates a request as an object (supports undo/queue). |
| Observer vs Mediator | Observer: Subject broadcasts to all. Mediator: objects route messages through a central hub. |
| When to use Builder? | When object has many optional fields. Avoids telescoping constructors. |
| When to use Singleton? | When exactly one shared instance is needed — DB connection, logger, config. |
| When to use Proxy? | When you need to add validation, logging, or access control in front of a real object without the caller knowing. |
| Template Method vs Strategy | Template Method uses inheritance — subclass overrides steps. Strategy uses composition — swap the whole algorithm at runtime. |
| When to use Template Method? | When multiple classes share the same algorithm flow but differ in specific steps. Avoids duplicate flow code across subclasses. |
