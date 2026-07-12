# Facade Pattern

## What is it?

Facade provides a **simplified, unified interface** over a complex subsystem.

The client calls one method on the Facade. The Facade internally coordinates multiple subsystems in the right order.
The client never needs to know about the subsystems, their order of execution, or how they depend on each other.

> Think of it as **placing an order on an e-commerce app**.
> You tap "Place Order" — one button.
> Behind the scenes: stock is checked, payment is charged, shipment is created, confirmation email is sent.
> You made ONE request. You never called Inventory, Payment, Shipping, or Notification yourself.

---

## Why it is a Structural Pattern

Structural patterns deal with **how objects are composed and connected**.

| Category    | Concern                               | Examples                      |
|-------------|---------------------------------------|-------------------------------|
| Creational  | How objects are **created**           | Factory, Builder, Singleton   |
| Structural  | How objects are **composed/connected**| Facade, Adapter, Proxy        |
| Behavioural | How objects **communicate/behave**    | Strategy, Observer, Template  |

Facade is structural because it **composes** multiple subsystem objects behind one interface.
It restructures how the client connects to the system — from many direct connections to one single entry point.

---

## 🥇 Real World Example — Order Placement

Placing an order involves multiple complex subsystems:

```
1. InventoryService   → check stock, reserve stock
2. PaymentService     → process payment, get transaction ID
3. ShippingService    → create shipment, assign tracking ID
4. NotificationService → send email confirmation
```

**Without Facade** — client must know all subsystems and call them in the right order:

```java
// BAD — client is coupled to 4 subsystems and must manage the whole flow
inventoryService.checkStock(productId, qty);
inventoryService.reserveStock(productId, qty);
String txnId = paymentService.processPayment(orderId, amount, method);
String trackingId = shippingService.createShipment(orderId, address);
notificationService.sendOrderConfirmation(orderId, email, trackingId);
// miss one step? wrong order? error handling? — all on the client
```

**With Facade** — client calls one method:

```java
// GOOD — client only knows OrderFacade
orderFacade.placeOrder(orderId, productId, qty, amount, method, address, email);
```

---

## Block Diagram

```
                     Client
                       │
                       │ placeOrder() / cancelOrder()
                       │
                  OrderFacade           ← single entry point
                       │
          ┌────────────┼─────────────┬──────────────────┐
          │            │             │                  │
  InventoryService  PaymentService  ShippingService  NotificationService
          │            │             │                  │
  checkStock()     processPayment()  createShipment()   sendConfirmation()
  reserveStock()   refundPayment()   cancelShipment()   sendCancellation()
  releaseStock()

  ─────────── Subsystems — Client never calls these directly ───────────
```

---

## How to Use It — 3 Steps

**Step 1: Build the subsystems (each has its own responsibility)**

```java
public class InventoryService {
    public boolean checkStock(String productId, int qty) { ... }
    public void reserveStock(String productId, int qty)  { ... }
}

public class PaymentService {
    public String processPayment(String orderId, double amount, String method) { ... }
}
// ... ShippingService, NotificationService
```

**Step 2: Create the Facade — wraps all subsystems, exposes simple methods**

```java
public class OrderFacade {

    private final InventoryService    inventoryService    = new InventoryService();
    private final PaymentService      paymentService      = new PaymentService();
    private final ShippingService     shippingService     = new ShippingService();
    private final NotificationService notificationService = new NotificationService();

    public boolean placeOrder(String orderId, String productId, int qty,
                              double amount, String paymentMethod,
                              String address, String email) {
        if (!inventoryService.checkStock(productId, qty)) return false;
        inventoryService.reserveStock(productId, qty);
        String txnId     = paymentService.processPayment(orderId, amount, paymentMethod);
        String trackingId = shippingService.createShipment(orderId, address);
        notificationService.sendOrderConfirmation(orderId, email, trackingId);
        return true;
    }
}
```

**Step 3: Client uses Facade only**

```java
OrderFacade facade = new OrderFacade();
facade.placeOrder("ORD-5001", "PROD-iPhone15", 1, 79999.0, "UPI", "Chennai", "john@example.com");
facade.cancelOrder("ORD-5001", "PROD-iPhone15", 1, "TXN-001", 79999.0, "TRK-ORD-5001", "john@example.com");
```

---

## Output

```
======== Place Order ========

[OrderFacade] Placing order: ORD-5001
  [InventoryService]    Checking stock for product: PROD-iPhone15
  [InventoryService]    Stock available: 1 unit(s)
  [InventoryService]    Stock reserved: 1 unit(s) for PROD-iPhone15
  [PaymentService]      Processing UPI payment of Rs.79999.0 for order ORD-5001
  [PaymentService]      Transaction ID: TXN-1720000001
  [ShippingService]     Shipment created for order ORD-5001
  [ShippingService]     Deliver to: 123, Anna Nagar, Chennai - 600040
  [ShippingService]     Tracking ID: TRK-ORD-5001
  [NotificationService] Email sent to john@example.com
  [NotificationService] Order: ORD-5001 | Track: TRK-ORD-5001
[OrderFacade] Order ORD-5001 placed successfully!

======== Cancel Order ========

[OrderFacade] Cancelling order: ORD-5001
  [NotificationService] Cancellation email sent to john@example.com
  [ShippingService]     Shipment cancelled | Tracking: TRK-ORD-5001
  [PaymentService]      Refunding Rs.79999.0 | TxnId: TXN-001
  [InventoryService]    Stock released back for PROD-iPhone15
[OrderFacade] Order ORD-5001 cancelled and refund initiated.
```

---

## Facade vs Adapter vs Proxy

| Pattern | Intent | Interface |
|---------|--------|-----------|
| **Facade** | Simplifies a **complex subsystem** behind one entry point | New, simpler interface |
| **Adapter** | Makes an **incompatible interface** compatible | Translates one interface to another |
| **Proxy** | Controls **access** to the real object | Same interface as real object |

---

## Where Facade is Used in Real Systems

| System | What Facade hides |
|--------|------------------|
| Spring `JdbcTemplate` | Hides Connection, Statement, ResultSet, exception handling |
| Spring `RestTemplate` | Hides HTTP client, connection management, response parsing |
| AWS SDK high-level clients | Hides low-level HTTP calls, signing, retry logic |
| E-commerce checkout | Hides inventory, payment, shipping, notification subsystems |
| Microservice API Gateway | Single entry point hiding multiple downstream microservices |

---

## SOLID Principles

| Principle | How Facade follows it |
|-----------|----------------------|
| Single Responsibility | Each subsystem does one thing. Facade only orchestrates — it doesn't implement business logic. |
| Open/Closed | Add new subsystem (e.g., LoyaltyService) = update Facade only. Client code untouched. |
| Dependency Inversion | Client depends on `OrderFacade` — not on `InventoryService` or `PaymentService` directly. |

---

## One Line Summary

> **Facade = One simple door into a complex building — the client knocks once, Facade handles everything inside.**
