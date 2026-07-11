# Decorator Pattern

## What is it?

Decorator **adds new behaviour to an existing object at runtime** without changing the object's class.

You wrap an object inside another object. The wrapper adds something extra and then delegates to the original.
You can keep wrapping — each layer adds more behaviour. The original never changes.

> Think of it as **toppings on a coffee** — you start with plain coffee.
> Add milk → still a coffee, but with milk.
> Add sugar on top → still a coffee, but with milk and sugar.
> The cup of coffee never changed. You just kept wrapping it.

---

## Why it is a Structural Pattern

Structural patterns deal with **how objects are composed and connected**.

| Category    | Concern                               | Examples                    |
|-------------|---------------------------------------|-----------------------------|
| Creational  | How objects are **created**           | Factory, Builder, Singleton |
| Structural  | How objects are **composed/connected**| Adapter, Decorator, Facade  |
| Behavioural | How objects **communicate/behave**    | Strategy, Observer, Command |

Decorator is structural because it **composes** objects by wrapping one inside another.
It builds a chain of objects — each one adding to the previous.
It does not create objects (Creational) and does not change how they interact (Behavioural).

---

## The Problem

You have `SimpleCoffee`. You want variants:

- Coffee with Milk
- Coffee with Sugar
- Coffee with Milk + Sugar
- Coffee with Milk + Sugar + Whip
- Coffee with double Sugar

**Without Decorator** — you'd need a class for every combination:

```
SimpleCoffee
CoffeeWithMilk
CoffeeWithSugar
CoffeeWithMilkAndSugar
CoffeeWithMilkSugarAndWhip
...
```

5 combinations today → 50 next week → **class explosion**.

**With Decorator** — you have 4 small classes and combine them freely at runtime.

---

## Block Diagram

```
                     Coffee
                   (interface)
                       ^
                       |  implements
               ─────────────────────
               |                   |
         SimpleCoffee        CoffeeDecorator
          (base object)      (abstract wrapper)
          Rs. 50                   ^
                                   | extends
                    ───────────────────────────
                    |              |           |
              MilkDecorator  SugarDecorator  WhipDecorator
               + Rs. 15         + Rs. 5       + Rs. 20


Wrapping at runtime:

  new WhipDecorator(
      new SugarDecorator(
          new MilkDecorator(
              new SimpleCoffee()
          )
      )
  )

Call chain for getCost():

  WhipDecorator.getCost()
       |
       └── SugarDecorator.getCost()
                |
                └── MilkDecorator.getCost()
                         |
                         └── SimpleCoffee.getCost()  ->  50
                         └── + 15  =  65
                └── + 5   =  70
       └── + 20  =  90
```

---

## How to Use It — 4 Steps

**Step 1: Define the component interface**

```java
public interface Coffee {
    String getDescription();
    double getCost();
}
```

**Step 2: Create the base object**

```java
public class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple Coffee"; }
    public double getCost()        { return 50.0; }
}
```

**Step 3: Create the abstract decorator**

```java
public abstract class CoffeeDecorator implements Coffee {

    protected final Coffee coffee;   // wraps any Coffee

    public CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }

    public String getDescription() { return coffee.getDescription(); }
    public double getCost()        { return coffee.getCost(); }
}
```

> **Key:** `CoffeeDecorator` IS-A `Coffee` AND HAS-A `Coffee`.
> IS-A → can replace any Coffee.
> HAS-A → can wrap any Coffee (including another decorator).

**Step 4: Create concrete decorators**

```java
public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }

    public String getDescription() { return coffee.getDescription() + " + Milk"; }
    public double getCost()        { return coffee.getCost() + 15.0; }
}
```

**Use it:**

```java
Coffee c = new WhipDecorator(new SugarDecorator(new MilkDecorator(new SimpleCoffee())));
System.out.println(c.getDescription());  // Simple Coffee + Milk + Sugar + Whip
System.out.println(c.getCost());         // 90.0
```

---

## Output

```
Simple Coffee                          -->  Rs.50.0
Simple Coffee + Milk                   -->  Rs.65.0
Simple Coffee + Milk + Sugar           -->  Rs.70.0
Simple Coffee + Milk + Sugar + Whip    -->  Rs.90.0
Simple Coffee + Sugar + Sugar          -->  Rs.60.0
```

---

## Pricing Breakdown

| Coffee                         | Calculation              | Total   |
|--------------------------------|--------------------------|---------|
| Simple Coffee                  | 50                       | Rs. 50  |
| + Milk                         | 50 + 15                  | Rs. 65  |
| + Milk + Sugar                 | 50 + 15 + 5              | Rs. 70  |
| + Milk + Sugar + Whip          | 50 + 15 + 5 + 20         | Rs. 90  |
| + Sugar + Sugar (double sugar) | 50 + 5 + 5               | Rs. 60  |

---

## Decorator vs Adapter — Most Asked Interview Question

| Decorator                                 | Adapter                                   |
|-------------------------------------------|-------------------------------------------|
| Keeps the **same interface**              | Changes the **interface**                 |
| Adds **new behaviour** to an object       | Makes **incompatible** systems work       |
| Wraps to **extend**                       | Wraps to **translate**                    |
| Example: Coffee → add Milk → add Sugar    | Example: RazorpaySDK → PaymentService     |

---

## SOLID Principles

| Principle | How Decorator follows it |
|-----------|--------------------------|
| Open/Closed | `SimpleCoffee` never changes. New topping = new decorator class only. |
| Single Responsibility | Each decorator does one thing — add its own description and cost. |
| Liskov Substitution | Any decorator can replace `SimpleCoffee` — both are `Coffee`. |

---

## One Line Summary

> **Decorator = Wrap an object to add behaviour, keeping the same interface, chainable at runtime.**
