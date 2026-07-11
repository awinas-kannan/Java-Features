# Adapter Pattern

## What is it?

Adapter allows two incompatible interfaces to work together **without modifying either side**.

You have your own interface. A third-party library has a completely different interface.
You cannot change the library. You write an Adapter that sits in between and translates.

> Think of it as a **power socket adapter** — your Indian plug, a UK socket.
> You don't rewire the plug. You don't rewire the wall. You use an adapter.

---

## Why it is a Structural Pattern

Structural patterns deal with **how objects are composed and connected**.

| Category        | Concern                                  | Examples                     |
|-----------------|------------------------------------------|------------------------------|
| Creational      | How objects are **created**              | Factory, Builder, Singleton  |
| Structural      | How objects are **composed/connected**   | Adapter, Decorator, Facade   |
| Behavioural     | How objects **communicate/behave**       | Strategy, Observer, Command  |

Adapter is structural because it **connects** two incompatible structures by wrapping one inside the other.
It does not create objects (Creational) and does not change behaviour (Behavioural).
It changes the **shape** of the interface so two systems can plug into each other.

---

## The Problem

Your checkout service expects every payment provider to expose:

```java
PaymentService.pay(double amount, String method)
PaymentService.refund(String transactionId, double amount)
```

But each third-party SDK has different method names:

```
RazorpaySDK  ->  razorpayPay(amount, method)   /  razorpayRefund(txnId, amount)
StripeSDK    ->  stripeCharge(amount, method)   /  stripeRefund(chargeId, amount)
PaypalSDK    ->  executePayment(orderId, amount) /  issueRefund(paymentId)
```

You cannot modify these SDKs. They are third-party libraries.

---

## Block Diagram

```
          Checkout / OrderService
                    |
                    | calls pay() and refund()
                    |
             PaymentService
              (interface)
                    |
       -----------------------------
       |             |             |
 RazorpayAdapter  StripeAdapter  PaypalAdapter
       |             |             |
       | translates  | translates  | translates
       |             |             |
  RazorpaySDK    StripeSDK     PaypalSDK
  razorpayPay()  stripeCharge() executePayment()
  razorpayRefund() stripeRefund() issueRefund()

  ─────────── Third-party · Cannot Modify ───────────
```

---

## How to Use It — 4 Steps

**Step 1: Define your target interface**

```java
public interface PaymentService {
    String pay(double amount, String method);
    boolean refund(String transactionId, double amount);
}
```

**Step 2: The third-party SDK exists (cannot modify)**

```java
public class RazorpaySDK {
    public String razorpayPay(double amount, String paymentMethod) { ... }
    public boolean razorpayRefund(String transactionId, double amount) { ... }
}
```

**Step 3: Write the Adapter**

```java
public class RazorpayAdapter implements PaymentService {

    private final RazorpaySDK razorpaySDK;

    public RazorpayAdapter(RazorpaySDK razorpaySDK) {
        this.razorpaySDK = razorpaySDK;
    }

    @Override
    public String pay(double amount, String method) {
        return razorpaySDK.razorpayPay(amount, method);   // translate
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        return razorpaySDK.razorpayRefund(transactionId, amount);   // translate
    }
}
```

**Step 4: Your code uses only the interface — never the SDK**

```java
PaymentService payment = new RazorpayAdapter(new RazorpaySDK());
String txnId = payment.pay(499.0, "UPI");
payment.refund(txnId, 499.0);

// Switch to Stripe — one line change, nothing else changes
PaymentService payment = new StripeAdapter(new StripeSDK());
```

---

## What the Adapter Translates

| Our Interface            | Razorpay SDK           | Stripe SDK           | PayPal SDK                    |
|--------------------------|------------------------|----------------------|-------------------------------|
| `pay(amount, method)`    | `razorpayPay()`        | `stripeCharge()`     | `executePayment(orderId, amt)`|
| `refund(txnId, amount)`  | `razorpayRefund()`     | `stripeRefund()`     | `issueRefund(paymentId)`      |

PayPal is the most interesting case — `issueRefund()` does not take an `amount` parameter.
The adapter absorbs that difference silently. The caller never knows.

---

## Key Structure

```
                   PaymentService
                    (interface)
                        ^
                        |  implements
               RazorpayAdapter
                        |
                        |  wraps (HAS-A)
                   RazorpaySDK
```

The adapter:
- **IS-A** `PaymentService` — so it can be used anywhere a `PaymentService` is expected
- **HAS-A** `RazorpaySDK` — so it can delegate the actual work to the SDK

---

## Adapter vs Decorator — Most Asked Interview Question

| Adapter                                   | Decorator                               |
|-------------------------------------------|-----------------------------------------|
| Changes the **interface**                 | Keeps the **same interface**            |
| Used to integrate **incompatible** systems | Used to **add behaviour** to an object |
| Structural — connects two shapes          | Structural — wraps to extend            |
| Example: Razorpay SDK → PaymentService    | Example: Coffee → add Milk → add Sugar  |

---

## SOLID Principles

| Principle | How Adapter follows it |
|-----------|------------------------|
| Open/Closed | Add new provider = new Adapter class only. Zero changes to existing code. |
| Dependency Inversion | Checkout code depends on `PaymentService` interface, not on `RazorpaySDK` directly. |
| Single Responsibility | Adapter's only job is translation. Business logic stays in the service layer. |

---

## One Line Summary

> **Adapter = Translator between your interface and a third-party API you cannot change.**
