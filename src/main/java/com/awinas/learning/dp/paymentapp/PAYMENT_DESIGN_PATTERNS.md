# Payment System — Design Patterns Complete Guide

## Patterns Used

| Pattern          | Role in this app                                          |
|------------------|-----------------------------------------------------------|
| Factory          | Picks the right provider factory by name                  |
| Abstract Factory | Creates a matched pair: PaymentService + RefundService    |
| Adapter          | Translates our interface into incompatible SDK calls      |
| Strategy         | Carries the payment method details (UPI / Card / NetBanking) |

---

## Block Diagram — Complete Flow

```
                                             Customer
                                                 |
                                                 |
                                          OrderService
                                                 |
                                                 |
                                  PaymentFactory (Factory Pattern)
                                                 |
                      ┌──────────────────────────┴──────────────────────────┐
                      |                                                     |
             RazorpayFactory                                       StripeFactory
           (Abstract Factory)                                    (Abstract Factory)
                      |                                                     |
                      |                                                     |
               RazorpayAdapter                                      StripeAdapter
     implements PaymentService                              implements PaymentService
             + RefundService                                      + RefundService
                (Adapter)                                            (Adapter)
                      |                                                     |
                      ▼                                                     ▼
                 RazorpaySDK                                           StripeSDK
               razorpayPay()                                       stripeCharge()
             razorpayRefund()                                       stripeRefund()
    - - - - - - - - - - - - - Third-party SDKs  ·  Cannot Modify - - - - - - - - - - - -

                      |                                                     |
                      └──────────────────────┬──────────────────────────────┘
                                             |
                                             | uses
                                             |
                                  PaymentStrategy (Strategy Pattern)
                                             |
                          ┌──────────────────┼──────────────────┐
                          |                  |                  |
                     UpiStrategy        CardStrategy      NetBankingStrategy
                      method=UPI        method=CARD       method=NETBANKING
                      upiId=...         cardNumber=...    bankName=...
```

---

## Package Structure

```
paymentapp/
│
├── Main.java
│
├── factory/
│   └── PaymentFactory.java              ← Factory Pattern
│
├── abstractfactory/
│   ├── PaymentProviderFactory.java      ← Abstract Factory interface
│   ├── RazorpayFactory.java             ← Concrete factory for Razorpay
│   └── StripeFactory.java               ← Concrete factory for Stripe
│
├── adapter/
│   ├── PaymentService.java              ← Target interface (payment)
│   ├── RefundService.java               ← Target interface (refund)
│   ├── RazorpayAdapter.java             ← Adapts RazorpaySDK (pay + refund)
│   └── StripeAdapter.java               ← Adapts StripeSDK   (pay + refund)
│
├── strategy/
│   ├── PaymentStrategy.java             ← Strategy interface
│   ├── UpiStrategy.java
│   ├── CardStrategy.java
│   └── NetBankingStrategy.java
│
├── sdk/
│   ├── RazorpaySDK.java                 ← Third-party (cannot modify)
│   └── StripeSDK.java                   ← Third-party (cannot modify)
│
└── service/
    └── OrderService.java                ← Business layer, uses all 4 patterns
```

---

## The Problem This Solves

Your application needs to:

1. Support **multiple payment providers** (Razorpay, Stripe, Adyen...)
2. Support **multiple payment methods** (UPI, Card, NetBanking...)
3. Support **both payment and refund** for each provider
4. Never let provider-specific code leak into business logic
5. Add a new provider without touching existing code

Without patterns, `OrderService` would look like this:

```java
// BAD - no patterns, nightmare to maintain
if (provider.equals("RAZORPAY")) {
    if (method.equals("UPI")) {
        razorpaySDK.razorpayPay(amount, "UPI", upiId);
    } else if (method.equals("CARD")) {
        razorpaySDK.razorpayPay(amount, "CARD", cardDetails);
    }
} else if (provider.equals("STRIPE")) {
    if (method.equals("UPI")) {
        stripeSDK.stripeCharge(amount, "UPI", upiId);
    }
    // ... and it keeps growing
}
```

Every new provider or method means modifying `OrderService`. With patterns, `OrderService` never changes.

---

## Complete Flow — Step by Step

```
Main.java
   |
   | orderService.processPayment("ORD-001", 499.0, "RAZORPAY", new UpiStrategy("john@oksbi"))
   |
   v
OrderService.processPayment()
   |
   |--- Step 1: FACTORY PATTERN
   |    PaymentFactory.getFactory("RAZORPAY")
   |         |
   |         └── returns new RazorpayFactory()
   |
   |--- Step 2: ABSTRACT FACTORY PATTERN
   |    factory.createPaymentService()
   |         |
   |         └── RazorpayFactory creates new RazorpayPaymentAdapter(razorpaySDK)
   |
   |--- Step 3: ADAPTER + STRATEGY PATTERN
   |    paymentService.pay(499.0, upiStrategy)
   |         |
   |         └── RazorpayPaymentAdapter.pay() is called
   |                  |
   |                  |-- reads strategy.getMethod()  -> "UPI"
   |                  |-- reads strategy.getDetails() -> "UPI ID: john@oksbi"
   |                  |
   |                  └── calls razorpaySDK.razorpayPay(499.0, "UPI", "UPI ID: john@oksbi")
   |                                |
   |                                └── [RazorpaySDK] prints and returns "RZP-1234567890"
   |
   └── OrderService prints: Payment SUCCESS | TxnId: RZP-1234567890
```

---

## Pattern 1: Strategy

**Where:** `strategy/` package

**What it does:** Encapsulates the payment method (UPI, Card, NetBanking) as an object. The method details travel with the strategy.

```
PaymentStrategy (interface)
    |-- getMethod()   -> "UPI" / "CARD" / "NETBANKING"
    |-- getDetails()  -> method-specific info
         |
         |-- UpiStrategy        -> getDetails() = "UPI ID: john@oksbi"
         |-- CardStrategy       -> getDetails() = "Card: ****-1111 | Expiry: 12/27"
         └-- NetBankingStrategy -> getDetails() = "Bank: HDFC | Account: John Doe"
```

**Key point:** The strategy is passed all the way through to the Adapter, which passes it to the SDK. `OrderService` never unpacks the strategy itself.

**Why Strategy and not just a String?**

If you pass `"UPI"` as a string, you lose the UPI ID. If you pass `"CARD"` as a string, you lose the card number. The strategy object bundles both `method type` and `method details` together cleanly.

```java
// Strategy in action inside the Adapter:
razorpaySDK.razorpayPay(
    amount,
    strategy.getMethod(),    // "UPI"
    strategy.getDetails()    // "UPI ID: john@oksbi"
);
```

---

## Pattern 2: Adapter

**Where:** `adapter/` package

**What it does:** Our application defines two clean interfaces. Each SDK has completely different method names. Adapters bridge the gap.

```
Our Interface             Razorpay SDK            Stripe SDK
--------------            ------------            ----------
PaymentService.pay()  ->  razorpayPay()      vs   stripeCharge()
RefundService.refund() -> razorpayRefund()   vs   stripeRefund()
```

The method names, parameter order, and return types are all different between SDKs. Without adapters, we'd need `if/else` blocks everywhere.

**Adapter mapping:**

```
RazorpayPaymentAdapter
    pay(amount, strategy)  ->  razorpaySDK.razorpayPay(amount, method, details)

RazorpayRefundAdapter
    refund(txnId, amount)  ->  razorpaySDK.razorpayRefund(txnId, amount)

StripePaymentAdapter
    pay(amount, strategy)  ->  stripeSDK.stripeCharge(amount, method, details)

StripeRefundAdapter
    refund(txnId, amount)  ->  stripeSDK.stripeRefund(txnId, amount)
```

**Why separate PaymentAdapter and RefundAdapter per provider?**

Single Responsibility Principle. Payment logic and refund logic are different concerns. A class should do one thing. Splitting them also makes it easier to mock in tests.

---

## Pattern 3: Abstract Factory

**Where:** `abstractfactory/` package

**What it does:** Creates a **matched family** of objects for one provider. You will never accidentally mix a Stripe PaymentAdapter with a Razorpay RefundAdapter.

```
PaymentProviderFactory (interface)
    |-- createPaymentService() -> PaymentService
    └-- createRefundService()  -> RefundService

RazorpayFactory implements PaymentProviderFactory
    |-- createPaymentService() -> new RazorpayPaymentAdapter(razorpaySDK)
    └-- createRefundService()  -> new RazorpayRefundAdapter(razorpaySDK)

StripeFactory implements PaymentProviderFactory
    |-- createPaymentService() -> new StripePaymentAdapter(stripeSDK)
    └-- createRefundService()  -> new StripeRefundAdapter(stripeSDK)
```

**Key guarantee:** Both `RazorpayPaymentAdapter` and `RazorpayRefundAdapter` share the same `RazorpaySDK` instance inside `RazorpayFactory`. This means consistent configuration, credentials, and state.

**Abstract Factory vs Factory — what is the difference?**

| Factory Pattern                     | Abstract Factory Pattern                     |
|-------------------------------------|----------------------------------------------|
| Creates ONE object                  | Creates a FAMILY of related objects          |
| `PaymentFactory.getFactory("X")`    | `factory.createPaymentService()`             |
| Returns `PaymentProviderFactory`    | Returns `PaymentService` + `RefundService`   |
| Decides WHICH factory to use        | Decides WHAT objects to create               |

In this app, `PaymentFactory` (Factory) returns `RazorpayFactory` (Abstract Factory). They work at different levels.

---

## Pattern 4: Factory

**Where:** `factory/PaymentFactory.java`

**What it does:** Takes a provider name string and returns the right `PaymentProviderFactory`. Callers don't need to import or know about `RazorpayFactory` or `StripeFactory`.

```java
PaymentFactory.getFactory("RAZORPAY")  ->  new RazorpayFactory()
PaymentFactory.getFactory("STRIPE")    ->  new StripeFactory()
PaymentFactory.getFactory("ADYEN")     ->  throws IllegalArgumentException
```

**Why is this useful?**

In real systems, the provider often comes from configuration, database, or request:

```java
String provider = order.getPaymentProvider();   // "RAZORPAY" from DB
PaymentProviderFactory factory = PaymentFactory.getFactory(provider);
```

You don't need a chain of `if/else` in your business logic. The factory centralises that decision.

---

## How All 4 Patterns Connect

```
OrderService
    |
    |  only knows about these abstractions:
    |     - PaymentFactory
    |     - PaymentProviderFactory
    |     - PaymentService
    |     - RefundService
    |     - PaymentStrategy
    |
    v
PaymentFactory.getFactory("RAZORPAY")          <- FACTORY PATTERN
    |
    v
RazorpayFactory                                <- ABSTRACT FACTORY PATTERN
    |-- createPaymentService()
    |       |
    |       v
    |   RazorpayPaymentAdapter                 <- ADAPTER PATTERN
    |       |
    |       v
    |   razorpaySDK.razorpayPay(
    |       amount,
    |       strategy.getMethod(),              <- STRATEGY PATTERN
    |       strategy.getDetails()             <- STRATEGY PATTERN
    |   )
    |
    └-- createRefundService()
            |
            v
        RazorpayRefundAdapter                  <- ADAPTER PATTERN
            |
            v
        razorpaySDK.razorpayRefund(txnId, amount)
```

---

## What OrderService Actually Sees

```java
public String processPayment(String orderId, double amount,
                              String provider, PaymentStrategy strategy) {

    // Factory: I ask for a factory by name, I don't pick the class myself
    PaymentProviderFactory factory = PaymentFactory.getFactory(provider);

    // Abstract Factory: I get a PaymentService, I don't know which adapter it is
    PaymentService paymentService = factory.createPaymentService();

    // Adapter + Strategy: I call pay(), adapter handles the SDK translation
    String transactionId = paymentService.pay(amount, strategy);

    return transactionId;
}
```

`OrderService` has **zero imports** from `sdk/`, `adapter/` concrete classes, or `abstractfactory/` concrete classes. It only depends on interfaces and `PaymentFactory`. This is the Dependency Inversion Principle.

---

## Adding a New Provider (e.g., Adyen)

**Files to create:**

```
sdk/AdyenSDK.java                          <- third-party SDK simulation
adapter/AdyenPaymentAdapter.java           <- implements PaymentService
adapter/AdyenRefundAdapter.java            <- implements RefundService
abstractfactory/AdyenFactory.java          <- implements PaymentProviderFactory
```

**One line to change:**

```java
// PaymentFactory.java - add one case
case "ADYEN":
    return new AdyenFactory();
```

**Files NOT touched:**

- `OrderService.java`       - zero changes
- `PaymentService.java`     - zero changes
- `RefundService.java`      - zero changes
- `PaymentStrategy.java`    - zero changes
- All existing adapters     - zero changes

This is the **Open/Closed Principle** — open for extension, closed for modification.

---

## Adding a New Payment Method (e.g., Wallet)

**One file to create:**

```java
// strategy/WalletStrategy.java
public class WalletStrategy implements PaymentStrategy {
    private final String walletId;

    public WalletStrategy(String walletId) { this.walletId = walletId; }

    public String getMethod()  { return "WALLET"; }
    public String getDetails() { return "Wallet ID: " + walletId; }
}
```

**Usage:**

```java
orderService.processPayment("ORD-007", 200.0, "RAZORPAY", new WalletStrategy("WALL-999"));
```

**Files NOT touched:** Everything else stays unchanged.

---

## Sample Output When You Run Main.java

```
======== DEMO 1: Razorpay + UPI ========

[OrderService] Processing payment for Order: ORD-001 | Amount: $499.0 | Provider: RAZORPAY | Method: UPI
  [RazorpaySDK] razorpayPay() -> amount=499.0 | method=UPI | details=UPI ID: john@oksbi | txnId=RZP-1720000001
[OrderService] Payment SUCCESS | Order: ORD-001 | TxnId: RZP-1720000001

======== DEMO 2: Stripe + Card ========

[OrderService] Processing payment for Order: ORD-002 | Amount: $1299.0 | Provider: STRIPE | Method: CARD
  [StripeSDK] stripeCharge() -> amount=1299.0 | method=CARD | details=Card: ****-1111 | Expiry: 12/27 | chargeId=STR-1720000002
[OrderService] Payment SUCCESS | Order: ORD-002 | TxnId: STR-1720000002

======== DEMO 5: Refund - Razorpay ========

[OrderService] Processing refund for Order: ORD-001 | TxnId: RZP-1720000001 | Provider: RAZORPAY
  [RazorpaySDK] razorpayRefund() -> txnId=RZP-1720000001 | amount=499.0
[OrderService] Refund SUCCESS | Order: ORD-001
```

---

## SOLID Principles Applied

| Principle                    | How                                                                 |
|------------------------------|---------------------------------------------------------------------|
| Single Responsibility (S)    | Each adapter does one thing: payment OR refund, not both            |
| Open/Closed (O)              | Add provider = new files only, zero modification to existing code   |
| Liskov Substitution (L)      | Any `PaymentService` impl can replace another without breaking code |
| Interface Segregation (I)    | `PaymentService` and `RefundService` are separate, not one fat interface |
| Dependency Inversion (D)     | `OrderService` depends on interfaces, never on concrete SDK classes |

---

## Interview Summary

**Q: Why Adapter and not just calling the SDK directly?**

If you call `razorpaySDK.razorpayPay()` directly in `OrderService`, then when you add Stripe you need to change `OrderService`. The Adapter isolates vendor-specific code so business logic never changes.

**Q: Why Abstract Factory and not just Factory?**

Factory creates one object. Abstract Factory creates a family. Here, payment and refund must always come from the same provider. Abstract Factory guarantees that — you can't accidentally create a Razorpay payment with a Stripe refund.

**Q: Why Strategy and not just passing a String for payment method?**

A string only carries the method name. The strategy carries the method name AND the method-specific data (UPI ID, card number, bank name). The caller doesn't need to know what data each method needs — the strategy object bundles it.

**Q: What is the relationship between Factory and Abstract Factory here?**

`PaymentFactory` (Factory Pattern) returns a `PaymentProviderFactory` (Abstract Factory). They work at different levels. Factory decides which Abstract Factory to use. Abstract Factory decides which objects to create.
