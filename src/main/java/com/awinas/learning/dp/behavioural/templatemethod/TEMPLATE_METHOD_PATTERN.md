# Template Method Pattern

## What is it?

Template Method defines the **skeleton of an algorithm** in a base class.
The overall flow is fixed. Specific steps are left for subclasses to fill in.
Subclasses override the steps — they never change the overall algorithm structure.

> Think of it as a **recipe framework**.
> Every restaurant follows: Take order → Prepare food → Serve → Bill.
> The steps are the same. How each restaurant prepares the food differs.
> The recipe framework never changes — only the cooking style does.

---

## Why it is a Behavioural Pattern

Behavioural patterns deal with **how objects communicate and share responsibility**.

| Category    | Concern                               | Examples                         |
|-------------|---------------------------------------|----------------------------------|
| Creational  | How objects are **created**           | Factory, Builder, Singleton      |
| Structural  | How objects are **composed/connected**| Adapter, Decorator, Proxy        |
| Behavioural | How objects **communicate/behave**    | Template Method, Strategy, Observer |

Template Method is behavioural because it defines **how the algorithm flows** and **how subclasses participate** in that flow by overriding specific steps.

---

## 🥇 Real World Example — Order Processing

Every order in an e-commerce system follows the same pipeline:

```
Validate Order → Calculate Price → Apply Discount → Process Payment → Send Confirmation
```

But the details of each step differ by order type:

| Step | Standard Order | Premium Order | International Order |
|------|---------------|---------------|---------------------|
| Validate | ✅ Same | ✅ Same | ✅ Same |
| Calculate Price | Base + Rs.49 delivery | Base (free delivery) | Base + Rs.499 shipping + 18% customs |
| Apply Discount | ❌ No discount | ✅ 10% loyalty discount | ❌ No discount |
| Process Payment | Standard gateway | Priority queue | Forex gateway |
| Send Confirmation | ✅ Same | ✅ Same | ✅ Same |

The **skeleton is identical** — only the highlighted steps differ per order type.

---

## Block Diagram

```
              OrderProcessor  (Abstract Class)
              ──────────────────────────────────────────
              processOrder()   ← TEMPLATE METHOD (final)
                   │
                   ├── 1. validateOrder()    ← concrete (same for all)
                   │
                   ├── 2. calculatePrice()   ← abstract (subclass MUST implement)
                   │
                   ├── 3. applyDiscount()    ← hook    (subclass CAN override)
                   │                                    default = no discount
                   ├── 4. processPayment()   ← abstract (subclass MUST implement)
                   │
                   └── 5. sendConfirmation() ← concrete (same for all)
                              │
                 ─────────────────────────────────
                 │             │                 │
      StandardOrderProcessor  PremiumOrderProcessor  InternationalOrderProcessor
         calculatePrice()         calculatePrice()       calculatePrice()
         processPayment()         applyDiscount()        processPayment()
                                  processPayment()
```

---

## Types of Steps

| Step Type | Who implements | Can subclass change? | Example |
|-----------|---------------|---------------------|---------|
| **Concrete** | Base class | No | `validateOrder()`, `sendConfirmation()` |
| **Abstract** | Subclass (must) | Must override | `calculatePrice()`, `processPayment()` |
| **Hook** | Base class (default) | Optional override | `applyDiscount()` (default = no discount) |

---

## How to Use It — 4 Steps

**Step 1: Abstract class with the template method marked `final`**

```java
public abstract class OrderProcessor {

    // TEMPLATE METHOD — skeleton, cannot be changed by subclasses
    public final void processOrder(String orderId, double basePrice) {
        validateOrder(orderId);           // concrete
        double price = calculatePrice(basePrice);  // abstract
        price = applyDiscount(price);     // hook
        processPayment(orderId, price);   // abstract
        sendConfirmation(orderId, price); // concrete
    }

    private void validateOrder(String orderId) {
        System.out.println("Order " + orderId + " validated");
    }

    protected abstract double calculatePrice(double basePrice);  // must override

    protected double applyDiscount(double price) { return price; } // optional

    protected abstract void processPayment(String orderId, double amount); // must override

    private void sendConfirmation(String orderId, double amount) {
        System.out.println("Confirmation sent for " + orderId);
    }
}
```

**Step 2: Concrete subclass — override only the steps it needs to change**

```java
public class PremiumOrderProcessor extends OrderProcessor {

    @Override
    protected double calculatePrice(double basePrice) {
        System.out.println("Free delivery for premium member");
        return basePrice;   // no delivery charge
    }

    @Override
    protected double applyDiscount(double price) {
        double discount = price * 0.10;
        System.out.println("10% loyalty discount: -Rs." + discount);
        return price - discount;
    }

    @Override
    protected void processPayment(String orderId, double amount) {
        System.out.println("Priority payment of Rs." + amount + " processed");
    }
}
```

**Step 3: Use — same call, different behaviour**

```java
OrderProcessor standard     = new StandardOrderProcessor();
OrderProcessor premium      = new PremiumOrderProcessor();
OrderProcessor international = new InternationalOrderProcessor();

standard.processOrder("ORD-001", 1000.0);
premium.processOrder("ORD-002", 1000.0);
international.processOrder("ORD-003", 1000.0);
```

---

## Output

```
--- Processing Order: ORD-STD-001 ---
[Validate]  Order ORD-STD-001 validated successfully
[Price]     Base: Rs.1000.0 + Delivery: Rs.49.0 = Rs.1049.0
[Payment]   Standard payment of Rs.1049.00 processed
[Confirm]   Confirmation sent for ORD-STD-001 | Final: Rs.1049.00
--- Order ORD-STD-001 Complete ---

--- Processing Order: ORD-PRM-002 ---
[Validate]  Order ORD-PRM-002 validated successfully
[Price]     Base: Rs.1000.0 + Delivery: FREE (Premium member)
[Discount]  Loyalty 10% applied: -Rs.100.00 → Rs.900.00
[Payment]   Priority payment of Rs.900.00 processed (Premium queue)
[Confirm]   Confirmation sent for ORD-PRM-002 | Final: Rs.900.00
--- Order ORD-PRM-002 Complete ---

--- Processing Order: ORD-INT-003 ---
[Validate]  Order ORD-INT-003 validated successfully
[Price]     Base: Rs.1000.0 + Intl Shipping: Rs.499.0 + Customs 18%: Rs.180.00 = Rs.1679.00
[Payment]   International payment of Rs.1679.00 via forex gateway
[Confirm]   Confirmation sent for ORD-INT-003 | Final: Rs.1679.00
--- Order ORD-INT-003 Complete ---
```

---

## Template Method vs Strategy — Most Asked Interview Question

| Template Method                              | Strategy                                     |
|----------------------------------------------|----------------------------------------------|
| Uses **inheritance** — subclass extends base | Uses **composition** — context HAS-A strategy |
| Algorithm skeleton is fixed in base class    | Entire algorithm is swappable at runtime     |
| Subclass overrides **specific steps**        | Strategy replaces the **whole algorithm**    |
| Compile-time decision (which subclass)       | Runtime decision (setStrategy)               |
| Example: Standard/Premium/Intl order steps  | Example: UPI / Card / NetBanking payment     |

---

## SOLID Principles

| Principle | How Template Method follows it |
|-----------|-------------------------------|
| Open/Closed | Add new order type = new subclass only. `OrderProcessor` never changes. |
| Single Responsibility | Base class owns the flow. Each subclass owns its specific step logic. |
| Don't Repeat Yourself (DRY) | `validateOrder()` and `sendConfirmation()` written once in base class — never duplicated. |

---

## One Line Summary

> **Template Method = Fix the algorithm skeleton in a base class. Let subclasses fill in the specific steps without changing the overall flow.**
