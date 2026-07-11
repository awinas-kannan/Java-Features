# Proxy Pattern

## What is it?

Proxy provides a **surrogate or placeholder** for another object to control access to it.

The proxy sits in front of the real object. Every call goes through the proxy first.
The proxy can add behaviour — checks, logging, caching — before forwarding to the real object.
The caller never knows it is talking to a proxy.

> Think of it as a **bank security guard** before the cashier.
> You want to withdraw money (real object = cashier).
> The guard checks your ID, checks your account limits, logs your entry.
> If all good, you reach the cashier. If not, you're blocked.
> The cashier doesn't deal with security — that's the guard's job.

---

## Simple Mental Model — Validation Layer

A Protection Proxy is essentially a **validation and interception layer** in front of the real object.

```
Caller
  |
  ▼
Proxy  ← Validation Layer
  |
  ├── Is amount within single txn limit?
  ├── Is daily cap exceeded?
  ├── Is user authenticated?
  ├── Log the request
  |
  | all checks pass
  ▼
Real Object  ← actual work happens here
```

The real object only does its one job (process payment).
All the "before" and "after" concerns live in the proxy.

> **Interview one-liner:**
> *"A Protection Proxy is a validation and interception layer — same interface as the real object, but controls what gets through and logs everything."*

---

## Why it is a Structural Pattern

Structural patterns deal with **how objects are composed and connected**.

| Category    | Concern                               | Examples                      |
|-------------|---------------------------------------|-------------------------------|
| Creational  | How objects are **created**           | Factory, Builder, Singleton   |
| Structural  | How objects are **composed/connected**| Adapter, Decorator, Proxy     |
| Behavioural | How objects **communicate/behave**    | Strategy, Observer, Command   |

Proxy is structural because it **wraps** a real object and controls access to it.
It doesn't change the interface (same as real object) — it just adds a layer in front.

---

## Types of Proxy

| Type                | What it does                                         | Real-world example                     |
|---------------------|------------------------------------------------------|----------------------------------------|
| Protection Proxy    | Controls access based on permissions or limits       | Payment limit check, auth guard        |
| Logging Proxy       | Logs every call before/after delegation              | Audit trail for all transactions       |
| Virtual Proxy       | Delays creation of expensive object until needed     | Lazy-load payment gateway connection   |
| Remote Proxy        | Represents an object in a different server/process   | Calling a remote payment microservice  |
| Caching Proxy       | Returns cached result instead of calling real object | Cache exchange rates, tax rules        |

**This example implements: Protection Proxy + Logging Proxy**

---

## Real World Scenario

Your checkout calls `PaymentGateway.pay()`.

But before money moves, you need to:
- Block payments over Rs.5,000 (single transaction limit)
- Block if user has already spent Rs.10,000 today (daily limit)
- Log every attempt with timestamp (audit trail)

**Without Proxy** — you'd put all this inside `RealPaymentGateway` or `CheckoutService`.
`RealPaymentGateway` becomes bloated with non-payment concerns.

**With Proxy** — `RealPaymentGateway` only processes payments.
`PaymentGatewayProxy` handles limits + logging.
Clean separation of concerns.

---

## Block Diagram

```
              CheckoutService / Caller
                        |
                        | calls pay(userId, amount)
                        |
               PaymentGatewayProxy          ← Proxy (Protection + Logging)
                        |
              ┌─────────┴──────────┐
              |                    |
     Check 1: Single          Check 2: Daily
     Txn Limit                Spend Limit
     (> Rs.5000 → BLOCK)      (> Rs.10000 → BLOCK)
              |
              | all checks pass
              |
       RealPaymentGateway                   ← Real Subject
              |
        actual payment
```

---

## How to Use It — 4 Steps

**Step 1: Define the subject interface**

```java
public interface PaymentGateway {
    String pay(String userId, double amount);
    boolean refund(String transactionId, double amount);
}
```

**Step 2: Real Subject — only does real work**

```java
public class RealPaymentGateway implements PaymentGateway {

    @Override
    public String pay(String userId, double amount) {
        String txnId = "TXN-" + System.currentTimeMillis();
        System.out.println("Payment processed | " + userId + " | Rs." + amount);
        return txnId;
    }
}
```

**Step 3: Proxy — same interface, adds checks before delegating**

```java
public class PaymentGatewayProxy implements PaymentGateway {

    private final PaymentGateway realGateway;   // HAS-A real object

    public PaymentGatewayProxy(PaymentGateway realGateway) {
        this.realGateway = realGateway;
    }

    @Override
    public String pay(String userId, double amount) {
        log("PAY request | " + userId + " | Rs." + amount);

        if (amount > 5_000) {
            log("BLOCKED - Single txn limit exceeded");
            return null;
        }

        // delegate to real gateway
        String txnId = realGateway.pay(userId, amount);
        log("PAY success | TxnId: " + txnId);
        return txnId;
    }
}
```

**Step 4: Caller uses the interface — never knows about proxy**

```java
// Inject proxy — caller thinks it's the real gateway
PaymentGateway gateway = new PaymentGatewayProxy(new RealPaymentGateway());

gateway.pay("USER-001", 1500.0);   // passes checks → reaches RealPaymentGateway
gateway.pay("USER-001", 6000.0);   // blocked by proxy → RealPaymentGateway never called
```

---

## Output

```
=== Normal Payment ===
[Proxy] PAY request | User: USER-001 | Amount: Rs.1500.0
  [RealPaymentGateway] Payment processed | User: USER-001 | Amount: Rs.1500.0 | TxnId: TXN-001
[Proxy] PAY success | TxnId: TXN-001 | Total today: Rs.1500.0

=== Exceeds Single Txn Limit ===
[Proxy] PAY request | User: USER-001 | Amount: Rs.6000.0
[Proxy] BLOCKED - Single transaction limit Rs.5000.0 exceeded
Result: null

=== Approaching Daily Limit ===
[Proxy] PAY request | User: USER-001 | Amount: Rs.3000.0
  [RealPaymentGateway] Payment processed | Amount: Rs.3000.0 | TxnId: TXN-002
[Proxy] PAY request | User: USER-001 | Amount: Rs.3000.0
  [RealPaymentGateway] Payment processed | Amount: Rs.3000.0 | TxnId: TXN-003
[Proxy] PAY request | User: USER-001 | Amount: Rs.3000.0
[Proxy] BLOCKED - Daily limit Rs.10000.0 exceeded. Already spent: Rs.7500.0

=== Refund ===
[Proxy] REFUND request | TxnId: TXN-001 | Amount: Rs.1500.0
  [RealPaymentGateway] Refund processed | TxnId: TXN-001 | Amount: Rs.1500.0
[Proxy] REFUND success | TxnId: TXN-001
```

---

## Proxy vs Decorator — Most Asked Interview Question

| Proxy                                        | Decorator                                     |
|----------------------------------------------|-----------------------------------------------|
| Controls **access** to the real object       | Adds **new behaviour** to the object          |
| Proxy often created internally (caller unaware) | Decorator assembled by caller (chained explicitly) |
| Focus: security, limits, logging, caching    | Focus: adding features (milk, sugar, whip)    |
| Real object may not be created at all (blocked) | Base object always executes                 |
| Example: limit check before payment          | Example: Coffee + Milk + Sugar                |

---

## Proxy vs Adapter

| Proxy                                        | Adapter                                       |
|----------------------------------------------|-----------------------------------------------|
| **Same interface** as the real object        | **Different interface** — translates it       |
| Controls access to the same type of object   | Makes incompatible types work together        |
| Example: PaymentGatewayProxy wraps PaymentGateway | Example: RazorpayAdapter wraps RazorpaySDK |

---

## Where Proxy is Used in Real Systems

| System | Proxy doing what |
|--------|-----------------|
| Spring `@Transactional` | AOP proxy wraps your method — adds begin/commit/rollback |
| Spring `@Cacheable` | Proxy checks cache before calling real method |
| Spring Security | Proxy checks authentication before calling controller |
| Hibernate Lazy Loading | Virtual proxy — loads DB data only when accessed |
| Payment fraud detection | Protection proxy — blocks suspicious transactions |
| API Rate Limiting | Protection proxy — blocks if too many requests |

---

## SOLID Principles

| Principle | How Proxy follows it |
|-----------|---------------------|
| Single Responsibility | `RealPaymentGateway` only processes payments. Proxy handles limits + logging. |
| Open/Closed | Add new check (e.g., fraud rule) = update proxy only. Real gateway untouched. |
| Dependency Inversion | Caller depends on `PaymentGateway` interface, not on `RealPaymentGateway` directly. |

---

## One Line Summary

> **Proxy = A gatekeeper that sits in front of the real object — same interface, but controls who gets through and logs everything.**
