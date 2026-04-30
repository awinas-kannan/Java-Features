# Java Notes

> A comprehensive reference guide covering Java core concepts, OOP principles, exception handling, and best practices.
>
> **Author:** Awinas Kannan M R

---

## Table of Contents

1. [Java Core Concepts](#1-java-core-concepts)
   - 1.1 [Variables in Java](#11-variables-in-java)
   - 1.2 [Instance Initialization Block vs Static Initialization Block](#12-instance-initialization-block-vs-static-initialization-block)
   - 1.3 [`public static void main(String[] args)`](#13-public-static-void-mainstring-args)
   - 1.4 [Java is Pass-by-Value](#14-java-is-pass-by-value)
   - 1.5 [`equals()` and `hashCode()`](#15-equals-and-hashcode)
   - 1.6 [Serialization and Deserialization](#16-serialization-and-deserialization)
   - 1.7 [Immutable Class](#17-immutable-class)
   - 1.8 [String Pool](#18-string-pool)
   - 1.9 [Java Memory Management](#19-java-memory-management)
2. [Object Oriented Programming](#2-object-oriented-programming)
   - 2.1 [Constructor](#21-constructor)
   - 2.2 [Access Modifiers](#22-access-modifiers)
   - 2.3 [Abstraction](#23-abstraction)
   - 2.4 [Encapsulation](#24-encapsulation)
   - 2.5 [Inheritance](#25-inheritance)
   - 2.6 [Polymorphism](#26-polymorphism)
   - 2.7 [Interface vs Abstract Class](#27-interface-vs-abstract-class)
   - 2.8 [Multiple Inheritance](#28-multiple-inheritance)
   - 2.9 [Enum](#29-enum)
3. [Exception Handling & Best Practices](#3-exception-handling--best-practices)
   - 3.1 [Exception Handling in Java](#31-exception-handling-in-java)
   - 3.2 [Types of Exceptions](#32-types-of-exceptions)
   - 3.3 [Exception Handling Keywords](#33-exception-handling-keywords)
   - 3.4 [Error vs Exception](#34-error-vs-exception)
   - 3.5 [What Happens if `finally` Block Throws an Exception?](#35-what-happens-if-finally-block-throws-an-exception)

---

## 1. Java Core Concepts

### 1.1 Variables in Java

In Java, variables are broadly classified based on where and how they are declared inside a class:

| Type | Where Declared | Belongs To | Lifetime | Access Method |
|------|---------------|------------|----------|---------------|
| **Instance Variable** (Non-Static) | Inside a class, but outside any method | An object (instance) | Created when an object is created | Accessed via object reference |
| **Static Variable** (Class Variable) | Inside a class with the `static` keyword | The class itself (shared by all objects) | Created when the class is loaded | Accessed via class name or object |
| **Local Variable** | Inside a method or constructor | — | Exists only during method execution | Accessed only within the method |

---

### 1.2 Instance Initialization Block vs Static Initialization Block

#### Instance Initialization Block

- All initializers are executed automatically in textual order for every object we create.
- Code for all instance initializers is executed **before** any constructor.
- An instance initializer cannot have a return statement.

#### Static Initialization Block

- A static initializer is executed **only once** for a class when the class definition is loaded into the JVM (whereas an instance initializer runs once per object).
- All static initializers are executed in textual order in which they appear, and execute **before** any instance initializers.

> **Refer:** `InstanceInitialization.java`, `StaticInstanceInitialization.java`

---

### 1.3 `public static void main(String[] args)`

| Keyword | Explanation |
|---------|-------------|
| `public` | Must be public so that the JVM can access it from anywhere outside the class. If it were `private`, `protected`, or default, the JVM would not be able to invoke it. |
| `static` | The main method is static so it can be called without creating an object of the class. If it weren't static, the JVM would need to create an instance first — but if the class has no default (no-arg) constructor, the JVM wouldn't know how to pass arguments. Hence, `static` allows the JVM to call it directly using the class name (e.g., `ClassName.main()`). |
| `void` | The main method returns no value because it is never called by other code in your program. It's the entry point called by the JVM, and there's no caller expecting a return value. |

> **Reference:** [HowToDoInJava — main method](https://howtodoinjava.com/java/basics/main-method/)

---

### 1.4 Java is Pass-by-Value

When you pass an object (instance) to a method, the **value of its reference** (i.e., the memory address) is copied bit by bit into the new parameter variable. This means both references point to the same object in memory.

However, if you **change the reference** inside the method (by assigning it to a new object), it only updates the local copy of the reference — the original reference in the caller remains unchanged.

If Java were pass-by-reference, then changing the reference inside the method would also change the original reference outside the method.

| Action Inside Method | Affects Original Object? | Why |
|---------------------|-------------------------|-----|
| Modifying object fields | Yes | Both references point to the same object |
| Reassigning the reference | No | The local copy of the reference is changed |

> **Refer:** `PassByValue.java`
>
> **Reference:** [HowToDoInJava — Pass by Value](https://howtodoinjava.com/java/basics/java-is-pass-by-value-lets-see-how/)

---

### 1.5 `equals()` and `hashCode()`

**`equals()`** — By default, the `equals()` method (in `Object` class) checks object references — meaning two objects are considered equal only if they refer to the same memory location. You can override it to compare object contents (field values) instead of memory addresses.

#### Why Override Both?

Whenever you override `equals()`, you should also override `hashCode()` to maintain the general contract between them:

> *If two objects are equal according to `equals()`, they must return the same hash code from `hashCode()`.*

This is essential because `hashCode()` is used in hash-based collections like `HashSet`, `HashMap`, and `HashTable` — which rely on hash codes for storing and retrieving objects efficiently.

#### Important Rules

- If two objects have the **same hash code**, it does **not** necessarily mean they are equal (this is called a **hash collision**).
- If two objects are **equal** (as per `equals()`), they **must** have the same hash code.

> **Refer:** `EqualsOverridenInStringClass.java`

---

### 1.6 Serialization and Deserialization

#### Serialization

Serialization is the process of converting a Java object into a **byte stream**. This byte stream can then be:

- Stored in a file
- Sent over a network
- Saved in a database
- Cached or persisted in session storage

In Java, a class must implement `java.io.Serializable` for its objects to be serialized using Java's built-in mechanisms.

#### Deserialization

Deserialization is the reverse process — converting the byte stream back into the original Java object.

#### How to Serialize an Object

1. Implement the `java.io.Serializable` interface (a marker interface with no methods).
2. Optionally define a `serialVersionUID` for version compatibility.

#### What is `serialVersionUID`?

`serialVersionUID` is a unique identifier for each `Serializable` class. It is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes that are compatible (same structure).

```java
private static final long serialVersionUID = 1L;
```

- If they differ, Java throws an `InvalidClassException`.
- If you don't define it manually, Java automatically generates one based on class name, fields, and other metadata. That means if you modify the class (e.g., add/remove a field), the automatically generated UID changes — making older serialized objects incompatible.
- **Always define a manual `serialVersionUID`** to maintain compatibility between versions of your class.

#### Serialization in Modern Frameworks (Spring Boot, REST, etc.)

| Context | Used For | Serializable Required? | Explanation |
|---------|----------|----------------------|-------------|
| REST APIs (JSON/XML) | Converting Java objects to/from JSON | No | Spring Boot uses Jackson (or Gson), not Java's native Serializable |
| Caching (e.g., Redis, Ehcache) | Storing objects in memory/disk | Yes | Cache implementations often use Java serialization |
| Messaging (JMS, Kafka) | Sending objects between systems | Yes | Objects must be serializable or converted to JSON/Avro |
| Session Replication (e.g., Tomcat cluster) | Sharing HTTP sessions | Yes | Session attributes must be Serializable |
| File I/O or Sockets | Persisting or transferring raw objects | Yes | Required when using `ObjectOutputStream` / `ObjectInputStream` |

> **Note:** `transient` values won't be available after serialization.
>
> **Refer:** `SerializationAnddeselialization`, `SerializationUtility`, `DeserializationUtility`
>
> **Reference:** [Baeldung — serialVersionUID](https://www.baeldung.com/java-serial-version-uid)

---

### 1.7 Immutable Class

An immutable class is a class whose instances **cannot be changed** after creation. Once the object is created, its state (fields/variables) cannot be modified.

**Famous example:** `String` in Java.

#### Rules to Create an Immutable Class

1. Declare the class as `final` — cannot be subclassed.
2. Declare all fields as `private final`.
3. Don't provide setter methods.
4. Initialize all fields via constructor.
5. If a field refers to a mutable object, perform **deep copy** in:
   - **Constructor** (to protect internal state)
   - **Getter** (to prevent external modification)
6. Perform cloning of objects in the getter methods to return a copy rather than the actual object reference.

#### Why Make a Class Immutable?

- **Thread-safe** — no synchronization needed.
- **Easy to reason about** (no side effects).
- **Safe for caching** or sharing between threads.
- **Can be used safely** in hash-based collections (`HashMap`, `HashSet`).
- **String pool** is possible only because `String` is immutable in Java.

> **Refer:** `ImmutableClass.java`, `TestMain.java`, `StringImmutable.java`, `StringPool.java`
>
> **References:**
> - [HowToDoInJava — Immutable Class](https://howtodoinjava.com/java/basics/how-to-make-a-java-class-immutable/)
> - [JournalDev — Immutable Class](https://www.journaldev.com/129/how-to-create-immutable-class-in-java)
> - [InterviewCake — Mutable](https://www.interviewcake.com/concept/java/mutable)

---

### 1.8 String Pool

#### What is the String Pool?

The **String Pool** (or String Intern Pool) is a special memory area inside the Java Heap (specifically inside the method area / metaspace depending on Java version) where String literals are stored.

Whenever you create a String literal:

```java
String s1 = "Java";
```

The JVM first checks if `"Java"` already exists in the string pool:
- If it **exists** — it reuses the same object reference.
- If it **doesn't exist** — it creates a new object in the pool.

#### Why Does the String Pool Exist?

- Strings are used heavily in Java (e.g., keys, class names, messages, etc.).
- They are **immutable** — their values never change once created.
- Since strings cannot be modified, reusing them is safe — no thread or code can alter their value.
- If Strings were mutable, two variables could share the same reference and one could accidentally modify the other's content — completely breaking the pool mechanism.

#### Example — String Pool Behavior

```java
public class StringPoolDemo {
    public static void main(String[] args) {
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");
        String s4 = s3.intern();
    }
}
```

**Step-by-step explanation:**

| Statement | What Happens |
|-----------|-------------|
| `String s1 = "Java"` | JVM looks in the String Pool for `"Java"`. Not there yet, so it creates one and returns the reference to `s1`. |
| `String s2 = "Java"` | JVM finds `"Java"` already in the pool — reuses the same reference. So `s1 == s2` is **true**. |
| `String s3 = new String("Java")` | The `new` keyword always creates a new String object in the **heap**, even if `"Java"` exists in the pool. So `s1 == s3` is **false**, but `s1.equals(s3)` is **true**. |
| `String s4 = s3.intern()` | `.intern()` checks the pool for `"Java"`. Since it exists, it returns the pooled instance reference. So `s1 == s4` is **true**. |

#### String Interning

You can manually move a String from heap to pool using the `.intern()` method:

> `.intern()` tells JVM: *"Put this String in the pool if it's not already there, and return the pooled instance reference."*

#### Why Immutability Enables Pooling

If Strings were mutable:

```java
String s1 = "Hello";
String s2 = "Hello";
s1.setCharAt(0, 'M'); // Suppose this was possible
```

Then both `s1` and `s2` would now become `"Mello"` — which would corrupt all shared references in the pool. Hence, **immutability guarantees safety** — once a String is stored in the pool, its content will never change.

---

### 1.9 Java Memory Management

Java's memory model is managed automatically by the **Java Virtual Machine (JVM)** — you don't manually allocate or free memory like in C or C++.

#### Stack Memory (Thread Memory)

Stack memory stores method calls, local variables, and references to objects (not the objects themselves). Each thread gets its own stack — so it's **thread-safe**.

| Property | Description |
|----------|-------------|
| Speed | Very fast access — LIFO (Last In, First Out) |
| Scope | Exists only while the method is executing |
| Storage | Primitive types (`int`, `boolean`, etc.) and references to heap objects |
| Lifetime | Automatically cleared when a method ends |
| Error | If too many nested calls → `StackOverflowError` |

#### Heap Memory (Object Memory)

Heap is a shared memory area used to store all Java objects (and their instance variables). This is managed by the **Garbage Collector (GC)**.

| Property | Description |
|----------|-------------|
| Storage | Objects and arrays |
| Access | Shared by all threads (so must handle synchronization) |
| Lifetime | Controlled by the Garbage Collector |
| Error | If memory exhausted → `OutOfMemoryError: Java heap space` |

#### Method Area (Metaspace in Java 8+)

Stores:
- Class metadata (class name, methods, fields, static variables)
- Runtime constant pool
- Bytecode of loaded classes

> **Note:** Metaspace replaced PermGen from Java 8 and grows dynamically with system memory.

---

## 2. Object Oriented Programming

### 2.1 Constructor

A constructor is a special method in a class that is used to **initialize objects**. It has the same name as the class and does **not** have a return type, not even `void`.

#### Default Constructor

- If no constructor is explicitly declared, the JVM automatically provides a default constructor.
- The default constructor has **no parameters** and initializes objects with default values (`0`, `null`, `false`).

#### Parameterized Constructor

- A constructor that accepts parameters to initialize an object with specific values.
- **Important:** Once you declare any constructor, the JVM does **NOT** provide a default constructor. If you want both default and parameterized, you must declare the default constructor yourself.

#### Private Constructor

- A private constructor restricts object creation from outside the class.
- Commonly used in **Singleton design pattern**.

#### Constructor Chaining

Constructor chaining refers to calling one constructor from another to reuse code.

**A) Within the same class** — Use `this()` to call another constructor (must be the first statement).

**B) Calling Superclass Constructor** — Use `super()` to call a constructor of the parent class (must be the first statement).

---

### 2.2 Access Modifiers

| Modifier | Same Class | Same Package | Subclass (Different Package) | Everywhere |
|----------|:----------:|:------------:|:----------------------------:|:----------:|
| `public` | Yes | Yes | Yes | Yes |
| `protected` | Yes | Yes | Yes | No |
| `default` (package-private) | Yes | Yes | No | No |
| `private` | Yes | No | No | No |

**Access Hierarchy:** `public` > `protected` > `default` (package-private) > `private`

#### Rules for Classes

- A **top-level class** can only be `public` or `default` (package-private).
- A top-level class **cannot** be `private` or `protected`:
  - `private` — would restrict class to nothing outside itself (impossible).
  - `protected` — would restrict access to subclasses only (but top-level class has no enclosing class to inherit protection from).

#### Rules for Inner Classes / Members

- Inner classes, methods, and variables can be declared as `public`, `protected`, `default`, or `private`.
- `protected` and `private` are commonly used for encapsulation within classes.

#### `protected` Access Modifier — Deep Dive

`protected` means the member is accessible:
1. Within the **same package** (like default/package-private).
2. By **subclasses**, even if the subclass is in a different package.

```java
// Package: com.example.parent
public class Parent {
    protected int protectedValue = 42;
}

// Package: com.example.child
public class Child extends Parent {
    public void showValue() {
        System.out.println(protectedValue); // Accessible because Child extends Parent
    }
}
```

**Why `protected` is not allowed for top-level classes:**

- Within the same package, it behaves like `default` access — so it's redundant.
- Across packages, subclasses in another package cannot even **see** the class to extend it if the class itself is `protected` at the top level.

> **Refer:** `OopsMain.java`, `AccessModifierSamePackage`
>
> **Reference:** [HowToDoInJava — Access Modifiers](https://howtodoinjava.com/java/oops/java-access-modifiers/)

---

### 2.3 Abstraction

**Abstraction** is the concept of hiding internal implementation details and showing only essential functionalities. It focuses on **what** an object does, not **how** it does it.

#### Key Points

- You **cannot** create an instance of an abstract class.
- Achieved through **abstract classes** and **interfaces**.
- Allows you to define a contract for clients, while hiding the internal complexity.

#### Real Java Example: `Map` Interface

```java
Map<String, String> map = new HashMap<>();
map.put("key1", "value1");    // Client uses put() to insert data
String val = map.get("key1"); // Client uses get() to retrieve data
```

- Client only sees `get()` and `put()` methods.
- Internal implementation (like `HashMap`, `LinkedHashMap`, or `TreeMap`) is hidden.
- This is **abstraction** in action.

---

### 2.4 Encapsulation

**Encapsulation** is the wrapping of data (variables) and methods that manipulate that data into a single unit (class). It hides the internal state of the object and allows access only through methods.

Often achieved using:
- **Private** instance variables (data hiding)
- **Public** getter and setter methods (controlled access)

#### Key Points

- **Encapsulation = Data Hiding + Implementation Hiding**
  - Data hiding: Variables are private
  - Implementation hiding: Logic to manipulate data is hidden

- **Abstraction vs Encapsulation:**
  - **Abstraction:** "What" the class can do (e.g., `put()` and `get()` in `Map`)
  - **Encapsulation:** "How" it does it (e.g., `HashMap` logic to store/retrieve values)

#### Important Notes

- All instance variables should be `private`.
- Access through `public` getter/setter methods.
- Use `final` for fields that shouldn't change.
- Changes to implementation won't affect clients (e.g., changing the password encoding algorithm doesn't affect the `User` class users).
- **Interfaces + Encapsulation:** You can define getters/setters in an interface and implement them in your encapsulated class.
- **Immutability** is closely related to encapsulation — all fields are `private` and `final`, no setters. Example: `String` class in Java.

> **Refer:** `DobEncapsulation.java`, `PasswordEncapsulation.java`, `EncapsulatedClassUser.java`
>
> **Reference:** [HowToDoInJava — Encapsulation](https://howtodoinjava.com/java/oops/encapsulation-in-java-and-its-relation-with-abstraction/)

---

### 2.5 Inheritance

**Inheritance** is an **IS-A** relationship between classes. A child class (subclass) inherits all non-private fields and methods from the parent class (superclass).

**Purpose:** Promote code reusability and logical hierarchy.

```java
ParentClass p = new ChildClass();
```

- **Member fields** are accessed from the **reference type** class.
- **Member methods** are accessed from the **actual instance** type.

#### Key Observations

- **Fields are not overridden**, they are **hidden**. Access depends on reference type.
- **Methods can be overridden**, executed based on actual object type.

#### Important Notes

- `private` fields/methods in parent cannot be inherited.
- Constructors are not inherited, but you can call `super()` to invoke parent constructor.
- Multiple inheritance via classes is **not allowed**, but can be achieved using interfaces.

> **Refer:** `InheritanceMain.java`

---

### 2.6 Polymorphism

**Polymorphism** allows an object to take many forms — i.e., the same method name or operator can behave differently based on context.

#### Compile-time Polymorphism (Static Binding / Method Overloading)

**Rules for Overloading:**

| Rule | Allowed? |
|------|----------|
| Method signature must vary (number, type, or order of parameters) | Yes |
| Changing only return type | Not considered overloading |
| Changing only access modifier or exception | Not considered overloading |
| Can overload `static`, `final`, or `private` methods | Yes (they belong to the class) |

> **Refer:** `MethodOverLoadingRules.java`

#### Runtime Polymorphism (Dynamic Binding / Method Overriding)

**Rules for Overriding:**

| Rule | Detail |
|------|--------|
| Method name & argument list | Must be the same |
| Return type | Can be same or **covariant** (child of parent's return type) |
| `private`, `static`, `final` methods | Cannot be overridden |
| Access modifier | Cannot be **reduced** (e.g., `public` → `protected` is not allowed) |
| Checked Exception in child | Cannot be **broader** than parent |

> **Refer:** `MethodOverRidingRulesChild.java`

#### Operator Overloading

- Java does **not** support custom operator overloading like C++.
- Only the `+` operator is overloaded — for numbers (addition) and strings (concatenation).

---

### 2.7 Interface vs Abstract Class

#### Core Purpose

| Concept | Purpose |
|---------|---------|
| **Abstract Class** | To share common behavior and state (fields + methods) among subclasses |
| **Interface** | To define contracts — i.e., what a class must do, not how it does it |

#### Evolution Timeline

| Java Version | Major Interface Feature |
|-------------|------------------------|
| Before Java 8 | Only abstract methods (no body) and constants allowed |
| Java 8 | Introduced `default` methods (with body) and `static` methods. Default methods can be overridden. Default can be `public` only (not `protected`, `private`). |
| Java 9 | Introduced `private` methods (to reuse logic inside interface). Useful when two default methods need to share code without exposing the helper method to implementing classes. |
| Java 15+ | Sealed interfaces introduced (limit which classes can implement) |

#### Feature Comparison

| Feature | Abstract Class | Interface (Java <= 7) | Interface (Java 8+) |
|---------|---------------|----------------------|---------------------|
| Keyword | `abstract class` | `interface` | `interface` |
| Methods | Abstract and non-abstract | Only abstract | Abstract + Default + Static + Private |
| Access Modifiers (methods) | `public` / `protected` / `private` | Implicitly `public abstract` | Same, but `default` / `static` / `private` allowed |
| Fields / Variables | Instance & static variables | Only `public static final` (constants) | Same |
| Constructors | Allowed | Not allowed | Not allowed |
| Multiple Inheritance | No (only one class) | Yes (multiple interfaces) | Yes |
| When to use | Partial implementation or shared state | Enforce a contract | Same, with extra flexibility |

#### When to Use What

| Design Intent | Prefer Abstract Class | Prefer Interface |
|--------------|----------------------|-----------------|
| Core Purpose | Share common state + behavior among related classes | Define a contract for unrelated classes |
| Relationship | Clear IS-A relationship (inheritance hierarchy) | Capability/behavior ("can-do") |
| State (fields) | Need instance variables or shared logic | No instance data — only constants or pure behavior |
| Partial Implementation | Some code is already implemented | Only default logic or helper utilities |
| Constructor | Need to initialize fields or perform setup | No object construction or internal state |
| Extensibility | A class can extend **only one** abstract class | A class can implement **multiple** interfaces |
| Backward Compatibility | Adding new methods can break subclasses | Adding new `default` methods does not break existing implementations (Java 8+) |

#### Mnemonics to Remember

- **Interface = Capability** — "Can do" something (e.g., `Refundable`, `Searchable`, `Printable`, `Serializable`)
- **Abstract Class = Base Identity** — "Is a" type with shared identity (e.g., `PaymentGateway`, `UserAccount`, `Vehicle`)

#### Examples

- **Abstract class:** `HttpServlet` is an abstract class which defines all life cycle methods (initialization, service, destruction) so that we can concentrate only on business logic.
- **Interface:** `Map` is an interface implemented by `HashMap`, `TreeMap`, `Hashtable`, `WeakHashMap`.

> **Refer:** `PrivateMethodInterfaceMain.java`, `PaymentDemoJava7`, `PaymentDemoJava8`, `PaymentDemoJava9`
>
> **References:**
> - [HowToDoInJava — Interfaces and Abstract Classes](https://howtodoinjava.com/java/oops/exploring-interfaces-and-abstract-classes-in-java/)
> - [HowToDoInJava — Private Interface Methods (Java 9)](https://howtodoinjava.com/java9/java9-private-interface-methods/)

---

### 2.8 Multiple Inheritance

**Multiple Inheritance** means a class can inherit features (methods) from more than one parent type.

- In C++, you can extend multiple classes — but Java does **not** allow multiple class inheritance because of ambiguity problems (the **"Diamond Problem"**).
- However, Java allows multiple inheritance through **interfaces** — because interfaces only define method contracts (no instance state).

#### How Java Supports Multiple Inheritance via Interfaces

A class can implement multiple interfaces, and there is no ambiguity if:
- Only method signatures are inherited (no implementation conflict)
- Or, conflicts are explicitly resolved by the class

#### 1. Constants (Static Final Variables)

Variables in interfaces are implicitly `public`, `static`, and `final` — hence they act as constants.

If two interfaces declare the same constant name, the reference must be **qualified with the interface name** to avoid ambiguity:

```java
// Allowed:
System.out.println(Interface1.PARAM);
System.out.println(Interface2.PARAM);

// Error:
System.out.println(PARAM); // The field PARAM is ambiguous
```

#### 2. Abstract Methods

- If they have the **same signature**, it's fine — the implementing class will provide a single implementation.
- If they differ by parameters or return types, they are treated as **overloaded methods**.

```java
// Same signature — allowed:
interface A { void display(); }
interface B { void display(); }

class Test implements A, B {
    public void display() { System.out.println("Display logic"); }
}
```

```java
// Overloaded — allowed:
interface A { void show(int x); }
interface B { void show(String y); }

class Test implements A, B {
    public void show(int x) { }
    public void show(String y) { }
}
```

#### 3. Default Methods

Introduced in Java 8, default methods allow interfaces to have implementation logic.

**Rule:** If two interfaces have a default method with the same signature, and both are implemented in a class, the class **must override** that method explicitly. Otherwise, the compiler throws a "duplicate default methods" error.

```java
interface Idefault1 {
    default void work() { System.out.println("Work from Idefault1"); }
}
interface Idefault2 {
    default void work() { System.out.println("Work from Idefault2"); }
}

class DefaultRules implements Idefault1, Idefault2 {
    @Override
    public void work() {
        Idefault1.super.work(); // choose which to call
        System.out.println("Resolved conflict");
    }
}
```

#### 4. Static Methods

- Static methods in interfaces **cannot be overridden**.
- They belong to the interface itself and must be called using the **interface name**.

```java
interface IStatic1 {
    static void greet() { System.out.println("Hello from IStatic1"); }
}
interface IStatic2 {
    static void greet() { System.out.println("Hello from IStatic2"); }
}

class StaticRules implements IStatic1, IStatic2 {
    void call() {
        IStatic1.greet();  // must call using interface name
        IStatic2.greet();
    }
}
```

> **Refer:** `ConstantsAndAbstraceMethod`, `IstaticRules`, `DefaultRules`

---

### 2.9 Enum

An **enum** (enumeration) is a special kind of class that:

- Represents a **fixed set of constants** (predefined values).
- Each constant is actually an **object** (instance) of the enum type.
- Enum values are implicitly `public`, `static`, and `final`.
- Enum constructors are always `private` (or package-private).
- Enums cannot extend other classes (because they already extend `java.lang.Enum`).

Before Java 5, developers used `public static final int` constants, but this approach wasn't type-safe or self-documenting. Enums solved that problem.

#### Basic Example

```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}
```

Internally, each of these (`MONDAY`, `TUESDAY`, etc.) is an instance of `Day`. Java automatically makes the enum class `final` and extends `java.lang.Enum<Day>`.

#### Key Internal Points

- Each enum constant is an **object** (instance) of the enum class.
- `ordinal` is automatically assigned starting from `0`.
- Enum constructors are `private` to prevent new instances.
- `values()` and `valueOf()` are compiler-generated static methods.
- All enums implicitly extend `java.lang.Enum` — they cannot extend another class.

#### Best Practices

- Use enums instead of `int` or `String` constants.
- Add fields and methods when behavior differs per constant.
- Never use `public` constructors.
- Enums are **thread-safe** and **singleton-like** per constant.

#### When to Use Enum vs Inheritance

**Use Enum** — for a finite, fixed set of constants:

| Example | Description |
|---------|-------------|
| `OrderStatus` | `PLACED`, `SHIPPED`, `DELIVERED`, `CANCELLED` — finite set of order states |
| `PaymentType` | `CARD`, `UPI`, `WALLET` — type-safe payment modes |
| `DiscountType` | `SEASONAL`, `CLEARANCE`, `FESTIVE` — each type may have its own calculation |
| `StoreType` | `ONLINE`, `OFFLINE`, `OUTLET` — fixed store types |
| `ShipmentMethod` | `STANDARD`, `EXPRESS`, `SAME_DAY` — shipping options |

**Use Inheritance** — for different behaviors sharing a common base:

| Example | Description |
|---------|-------------|
| `Payment` | Abstract class with `pay()`, subclasses: `CardPayment`, `UpiPayment` — different behavior per type |
| `Product` | Base: `Product`, subclasses: `Electronics`, `Clothing`, `Groceries` — extra attributes or methods |
| `Customer` | Base: `Customer`, subclasses: `RegularCustomer`, `PremiumCustomer` — different discounts or loyalty rules |
| `Employee` | Base: `Employee`, subclasses: `Cashier`, `StoreManager` — different responsibilities |
| `Delivery` | Base: `Delivery`, subclasses: `StandardDelivery`, `ExpressDelivery` — different delivery logic |

> **Refer:** `EnumMain.java`

---

## 3. Exception Handling & Best Practices

### 3.1 Exception Handling in Java

Exception handling in Java is a mechanism to handle **runtime errors**, so that the normal flow of the program is not interrupted. It allows developers to detect, handle, and recover from exceptional conditions gracefully.

When an exception object is created in our application, we have two choices:
1. Handle it **within the method**.
2. Pass it to the **caller method** to let it handle.

#### How to Explain in an Interview (Concise Version)

> *"Exception handling in Java allows us to handle runtime errors gracefully. It prevents abnormal termination and helps maintain normal program flow. There are two main types: Checked exceptions, which are verified at compile time (like IOException or SQLException), and Unchecked exceptions, which occur at runtime due to programming mistakes (like NullPointerException or ArithmeticException). Errors, on the other hand, represent serious system issues that the application shouldn't handle, like OutOfMemoryError. We use keywords like try, catch, finally, throw, and throws to implement exception handling."*

---

### 3.2 Types of Exceptions

#### Checked Exceptions

- Checked at **compile time**.
- Compiler forces you to handle them using `try-catch` or `throws`.
- Usually occur due to external factors — file not found, network down, DB issue, etc.
- Subclass of `Exception` (but **not** `RuntimeException`).
- **Examples:** `IOException`, `SQLException`, `FileNotFoundException`, `ClassNotFoundException`

#### Unchecked Exceptions (Runtime Exceptions)

- Checked at **runtime**.
- Not required to handle or declare explicitly.
- Occur due to **programming mistakes** (logic errors).
- Subclass of `RuntimeException`.
- **Examples:** `NullPointerException`, `ArithmeticException`, `ArrayIndexOutOfBoundsException`, `IllegalArgumentException`

#### Errors

- Not technically exceptions — they represent **serious system failures**.
- Not meant to be caught or handled.
- **Examples:** `OutOfMemoryError`, `StackOverflowError`

---

### 3.3 Exception Handling Keywords

| Keyword | Purpose |
|---------|---------|
| `try` | Block of code that may throw an exception |
| `catch` | Block used to handle the exception |
| `finally` | Block that **always** executes (used for resource cleanup) |
| `throw` | Used to explicitly throw an exception (e.g., `throw new CustomException()`) |
| `throws` | Declares exceptions a method might throw |

---

### 3.4 Error vs Exception

Both `Error` and `Exception` are subclasses of `Throwable`.

- An **Error** represents serious system-level problems (like JVM crashes or memory issues) that a program should **not** attempt to handle.
- An **Exception** represents problems that can occur during execution but **can be handled** or recovered from using `try-catch` or `throws`.

| Aspect | Error | Exception |
|--------|-------|-----------|
| **Definition** | Serious problems beyond the control of the application | Conditions that a program can handle and recover from |
| **Package** | `java.lang.Error` | `java.lang.Exception` |
| **Recoverable?** | Usually **not** recoverable | Usually **recoverable** using try-catch or throws |
| **Caused By** | System failures — JVM issues, memory problems, hardware crash | Logical or runtime issues — invalid input, null references, arithmetic errors |
| **Examples** | `OutOfMemoryError`, `StackOverflowError`, `VirtualMachineError` | `IOException`, `NullPointerException`, `SQLException`, `ArithmeticException` |
| **Handling** | Should **not** be caught or handled | Should be caught or declared |
| **Hierarchy** | Subclass of `Throwable` but not of `Exception` | Subclass of `Throwable` |
| **Occurs at** | Mostly during runtime | Checked → compile time; Unchecked → runtime |

---

### 3.5 What Happens if `finally` Block Throws an Exception?

If a `finally` block throws an exception, it will **override** any exception thrown in the `try` or `catch` block. This means the original exception is **lost**, and only the one from the `finally` block is propagated.

**Why this is bad practice:**

- The JVM ensures that the `finally` block always executes (unless the JVM crashes or `System.exit()` is called).
- If `finally` itself throws an exception, it **suppresses or replaces** the original one.

**Output example:**

```
Catch block executed
Finally block executed
Exception in thread "main" java.lang.RuntimeException: Exception from finally
```

**Best Practice:** If cleanup code might throw an exception, handle it within the `finally` block using a **nested try-catch**.

---

> *End of Java Notes*
