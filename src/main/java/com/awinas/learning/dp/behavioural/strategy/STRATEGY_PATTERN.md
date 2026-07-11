# Strategy Pattern

## What is it?

Strategy defines a **family of algorithms**, puts each in its own class, and makes them **swappable at runtime**.

The object that uses the algorithm (called the Context) does not know or care which algorithm runs.
You just swap the strategy object and the behaviour changes.

> Think of it as **payment method at checkout** — the same checkout page, but you choose UPI, Card, or NetBanking.
> The checkout process doesn't change. Only the payment algorithm changes.

---

## Why it is a Behavioural Pattern

Behavioural patterns deal with **how objects communicate and share responsibility**.

| Category    | Concern                               | Examples                      |
|-------------|---------------------------------------|-------------------------------|
| Creational  | How objects are **created**           | Factory, Builder, Singleton   |
| Structural  | How objects are **composed/connected**| Adapter, Decorator, Facade    |
| Behavioural | How objects **communicate/behave**    | Strategy, Observer, Command   |

Strategy is behavioural because it changes **how** the Context behaves at runtime by delegating the algorithm to a strategy object.
The Context and Strategy communicate — Context calls the strategy, strategy carries out the work.

---

## The Problem

Your `CheckoutService` needs to support UPI, Card, and NetBanking.

**Without Strategy:**

```java
// BAD - every new method requires modifying CheckoutService
public void pay(String method, double amount) {
    if (method.equals("UPI")) {
        // UPI logic
    } else if (method.equals("CARD")) {
        // Card logic
    } else if (method.equals("NETBANKING")) {
        // NetBanking logic
    }
    // add WALLET? modify this method again
}
```

Every new payment method = modify `CheckoutService`. Violates Open/Closed Principle.

**With Strategy:**

```java
// CheckoutService never changes, ever
public void pay(String orderId, double amount) {
    paymentStrategy.getMethod();
    paymentStrategy.getDetails();
}
```

Add Wallet? Create `WalletStrategy`. `CheckoutService` untouched.

---

## Block Diagram

```
              CheckoutService
               (Context)
                    |
                    | uses
                    |
             PaymentStrategy
              (interface)
                    |
       ─────────────────────────
       |             |         |
  UpiStrategy  CardStrategy  NetBankingStrategy
   method=UPI  method=CARD   method=NETBANKING
   upiId=...   card=****1111  bank=HDFC


Runtime swap:

  checkout.setPaymentStrategy(new UpiStrategy("john@oksbi"))
                    |
  checkout.setPaymentStrategy(new CardStrategy("4111...", "12/27"))
                    |
  checkout.setPaymentStrategy(new NetBankingStrategy("HDFC"))

  CheckoutService stays the same throughout.
```

---

## How to Use It — 4 Steps

**Step 1: Define the strategy interface**

```java
public interface PaymentStrategy {
    void pay(double amount);    // behaviour — executes the payment algorithm
    String getMethod();         // metadata — used for logging / SDK integration
    String getDetails();        // metadata — method-specific info
}
```

> `pay()` is the core Strategy behaviour.
> `getMethod()` and `getDetails()` are useful when the strategy data needs to be passed
> to an external SDK or written to an audit log — they don't replace `pay()`, they complement it.

**Step 2: Create concrete strategies — each owns its own logic**

```java
public class UpiStrategy implements PaymentStrategy {
    private final String upiId;
    public UpiStrategy(String upiId) { this.upiId = upiId; }

    @Override
    public void pay(double amount) {
        System.out.println("Sending UPI payment | ID: " + upiId + " | Rs." + amount);
        // all UPI-specific logic lives HERE
    }

    @Override public String getMethod()  { return "UPI"; }
    @Override public String getDetails() { return "UPI ID: " + upiId; }
}

public class CardStrategy implements PaymentStrategy {
    private final String cardNumber, expiry;

    @Override
    public void pay(double amount) {
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        System.out.println("Charging card ****" + last4 + " | Rs." + amount);
    }

    @Override public String getMethod()  { return "CARD"; }
    @Override public String getDetails() { return "Card: ****-" + last4 + " | Expiry: " + expiry; }
}
```

**Step 3: Create the Context**

```java
public class CheckoutService {

    private PaymentStrategy paymentStrategy;

    public CheckoutService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void pay(String orderId, double amount) {
        System.out.println("[CheckoutService] Order: " + orderId);
        paymentStrategy.pay(amount);   // delegate — doesn't know which strategy runs
    }
}
```

**Step 4: Use it — swap freely**

```java
CheckoutService checkout = new CheckoutService(new UpiStrategy("john@oksbi"));
checkout.pay("ORD-001", 499.0);   // pays via UPI

checkout.setPaymentStrategy(new CardStrategy("4111111111111111", "12/27"));
checkout.pay("ORD-002", 1299.0);  // pays via Card — checkout unchanged
```

---

## Output

```
[CheckoutService] Order: ORD-001 | Amount: Rs.499.0  | Method: UPI        | UPI ID: john@oksbi
[CheckoutService] Order: ORD-002 | Amount: Rs.1299.0 | Method: CARD       | Card: ****-1111 | Expiry: 12/27
[CheckoutService] Order: ORD-003 | Amount: Rs.850.0  | Method: NETBANKING | Bank: HDFC
```

---

## Adding a New Payment Method

**One file to create:**

```java
public class WalletStrategy implements PaymentStrategy {
    private final String walletId;
    public WalletStrategy(String walletId) { this.walletId = walletId; }

    public String getMethod()  { return "WALLET"; }
    public String getDetails() { return "Wallet: " + walletId; }
}
```

**Files NOT touched:** `CheckoutService`, `UpiStrategy`, `CardStrategy`, `NetBankingStrategy` — zero changes.

---

## Strategy vs Other Patterns — Interview Comparison

| Strategy                                    | Command                                      | Template Method                          |
|---------------------------------------------|----------------------------------------------|------------------------------------------|
| Swaps the **algorithm** at runtime          | Encapsulates a **request** as an object      | Defines skeleton, subclasses fill steps  |
| Uses **composition** (HAS-A strategy)       | Uses **composition** (HAS-A command)         | Uses **inheritance**                     |
| Caller chooses the algorithm                | Caller queues/executes commands              | Subclass overrides specific steps        |
| Example: UPI / Card / NetBanking            | Example: Open file / Close file / Write file | Example: Abstract report generator      |

---

## Strategy vs Adapter

| Strategy                                  | Adapter                                   |
|-------------------------------------------|-------------------------------------------|
| Behavioural — changes **HOW** to do work  | Structural — changes **interface shape**  |
| Algorithms are interchangeable            | Makes incompatible systems work together  |
| Same interface, different behaviour       | Different interfaces, translation needed  |
| Example: UPI, Card, NetBanking strategies | Example: RazorpaySDK → PaymentService     |

---

## SOLID Principles

| Principle | How Strategy follows it |
|-----------|-------------------------|
| Open/Closed | Add new payment method = new class only. `CheckoutService` never changes. |
| Single Responsibility | Each strategy class has one job — carry its own method and details. |
| Dependency Inversion | `CheckoutService` depends on `PaymentStrategy` interface, not on `UpiStrategy` directly. |

---

## One Line Summary

> **Strategy = Swap the algorithm at runtime by changing the strategy object, without touching the Context.**
