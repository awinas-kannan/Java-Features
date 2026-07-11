# Observer Pattern

## What is it?

Observer defines a **one-to-many dependency** between objects.

When one object (Subject) changes state, all its dependents (Observers) are notified and updated automatically.

The Subject doesn't know what the observers do. It just broadcasts. Each observer decides how to react.

> Think of it as a **newspaper subscription**.
> The newspaper (Subject) publishes a new edition.
> Every subscriber (Observer) receives it automatically.
> The newspaper doesn't call each subscriber individually —
> it just publishes, and all subscribers are notified.

---

## Why it is a Behavioural Pattern

Behavioural patterns deal with **how objects communicate and share responsibility**.

| Category    | Concern                               | Examples                      |
|-------------|---------------------------------------|-------------------------------|
| Creational  | How objects are **created**           | Factory, Builder, Singleton   |
| Structural  | How objects are **composed/connected**| Adapter, Decorator, Proxy     |
| Behavioural | How objects **communicate/behave**    | Observer, Strategy, Command   |

Observer is behavioural because it defines the **communication protocol** between Subject and Observers.
The Subject broadcasts. Observers listen and respond. It is entirely about how objects talk to each other.

---

## Real World Scenario — Order Status Change

When an order status changes, multiple services need to react:

```
Order: PLACED → CONFIRMED → SHIPPED → DELIVERED
              |
              | notifies all registered observers automatically
              |
    ┌─────────┼──────────────┬──────────────┐
    |         |              |              |
EmailService  SMSService  WarehouseService  LoyaltyService
  (every     (SHIPPED,    (CONFIRMED,       (DELIVERED
  status)    DELIVERED,   SHIPPED,          only →
             CANCELLED)   CANCELLED)        add points)
```

**Without Observer** — `Order` would need to know about every service:

```java
// BAD - Order coupled to every downstream service
public void setStatus(OrderStatus status) {
    this.status = status;
    emailService.sendEmail(orderId, status);     // direct call
    smsService.sendSMS(orderId, status);         // direct call
    warehouseService.process(orderId, status);   // direct call
    loyaltyService.addPoints(orderId, status);   // direct call
    // add new service? modify Order class again
}
```

**With Observer** — `Order` knows nothing about who is listening:

```java
public void setStatus(OrderStatus status) {
    this.status = status;
    notifyObservers();   // that's it — broadcast and forget
}
```

---

## Block Diagram

```
              Order  (Subject)
              ─────────────────────────────
              orderId
              status
              observers: [EmailService,
                          SMSService,
                          WarehouseService,
                          LoyaltyService]
              ─────────────────────────────
              addObserver(observer)
              removeObserver(observer)
              setStatus(status)  ──────────► notifyObservers()
                                                    |
                                    ┌───────────────┼───────────────┐
                                    |               |               |
                                    ▼               ▼               ▼
                              EmailService    SMSService     WarehouseService
                              update()        update()        update()
                              (every status)  (SHIPPED,       (CONFIRMED,
                                              DELIVERED,      SHIPPED,
                                              CANCELLED)      CANCELLED)
                                                              LoyaltyService
                                                              update()
                                                              (DELIVERED only)
```

---

## How to Use It — 4 Steps

**Step 1: Define the Observer interface**

```java
public interface OrderObserver {
    void update(String orderId, OrderStatus status);
}
```

**Step 2: Create the Subject (Order)**

```java
public class Order {
    private final List<OrderObserver> observers = new ArrayList<>();

    public void addObserver(OrderObserver observer)    { observers.add(observer); }
    public void removeObserver(OrderObserver observer) { observers.remove(observer); }

    public void setStatus(OrderStatus newStatus) {
        this.status = newStatus;
        notifyObservers();         // broadcast to all
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(orderId, status);
        }
    }
}
```

**Step 3: Create Concrete Observers — each reacts differently**

```java
// EmailService — reacts to every status
public class EmailService implements OrderObserver {
    public void update(String orderId, OrderStatus status) {
        // send email based on status
    }
}

// LoyaltyService — only reacts to DELIVERED
public class LoyaltyService implements OrderObserver {
    public void update(String orderId, OrderStatus status) {
        if (status == OrderStatus.DELIVERED) {
            // add reward points
        }
    }
}
```

**Step 4: Register observers and trigger events**

```java
Order order = new Order("ORD-2001");
order.addObserver(new EmailService());
order.addObserver(new SMSService());
order.addObserver(new WarehouseService());
order.addObserver(new LoyaltyService());

order.setStatus(OrderStatus.CONFIRMED);   // all 4 observers notified automatically
order.setStatus(OrderStatus.SHIPPED);     // all 4 observers notified automatically
```

---

## Each Observer Reacts Differently

| Status      | EmailService | SMSService | WarehouseService       | LoyaltyService   |
|-------------|-------------|------------|------------------------|------------------|
| PLACED      | ✅ Send email | ❌ No SMS  | ❌ No action           | ❌ No action     |
| CONFIRMED   | ✅ Send email | ❌ No SMS  | ✅ Pick and pack        | ❌ No action     |
| SHIPPED     | ✅ Send email | ✅ Send SMS | ✅ Handover to courier  | ❌ No action     |
| DELIVERED   | ✅ Send email | ✅ Send SMS | ❌ No action           | ✅ Add 50 points |
| CANCELLED   | ✅ Send email | ✅ Send SMS | ✅ Restock items        | ❌ No action     |

This is the power of Observer — each service independently decides what to do. `Order` knows nothing about this logic.

---

## Dynamic Subscribe / Unsubscribe

Observers can be added or removed at runtime:

```java
SMSService sms = new SMSService();
order.addObserver(sms);

order.setStatus(OrderStatus.PLACED);     // SMS fires

order.removeObserver(sms);              // customer opted out of SMS

order.setStatus(OrderStatus.SHIPPED);   // SMS does NOT fire — email still does
```

Real-world use: customer opts out of SMS notifications — no code change needed in `Order`.

---

## Output

```
[Order] ORD-2001 status changed: null -> PLACED
  [EmailService]    Order confirmation email sent for ORD-2001
  [WarehouseService] No action for PLACED

[Order] ORD-2001 status changed: PLACED -> CONFIRMED
  [EmailService]    Order confirmed email sent for ORD-2001
  [WarehouseService] Pick and pack initiated for order ORD-2001

[Order] ORD-2001 status changed: CONFIRMED -> SHIPPED
  [EmailService]    Shipping email with tracking link sent for ORD-2001
  [SMSService]      SMS sent: Your order ORD-2001 is on the way!
  [WarehouseService] Order ORD-2001 handed to courier. Inventory updated.

[Order] ORD-2001 status changed: SHIPPED -> DELIVERED
  [EmailService]    Delivery confirmation email sent for ORD-2001
  [SMSService]      SMS sent: Order ORD-2001 delivered. Rate your experience.
  [LoyaltyService]  50 reward points added for order ORD-2001
```

---

## Observer in Real Systems

| System | How Observer is used |
|--------|---------------------|
| Spring `@EventListener` | Method subscribes to `ApplicationEvent` — Observer pattern built-in |
| Kafka / Event Streaming | Producer (Subject) publishes events, Consumers (Observers) react |
| Java `PropertyChangeListener` | UI components observe model changes |
| E-commerce OMS | Order events trigger Email, SMS, Warehouse, Finance |
| Stock trading app | Price change notifies all investors watching that stock |

---

## Observer vs Other Patterns

| Observer                                   | Strategy                                      |
|--------------------------------------------|-----------------------------------------------|
| One Subject → many Observers (1 to many)   | One Context → one Strategy at a time          |
| Observers react to **events**              | Strategy decides **how** to do something      |
| Subject broadcasts, doesn't choose who reacts | Context chooses which strategy to use      |
| Example: Order status → notify all services | Example: Checkout pays via UPI or Card       |

---

## SOLID Principles

| Principle | How Observer follows it |
|-----------|-------------------------|
| Open/Closed | Add new service (e.g., PushNotificationService) = new class only. `Order` untouched. |
| Single Responsibility | `Order` manages status. Each service manages its own notification logic. |
| Dependency Inversion | `Order` depends on `OrderObserver` interface, not on `EmailService` or `SMSService` directly. |

---

## One Line Summary

> **Observer = Subject broadcasts a state change, all registered observers are notified automatically — Subject and Observers are loosely coupled.**
