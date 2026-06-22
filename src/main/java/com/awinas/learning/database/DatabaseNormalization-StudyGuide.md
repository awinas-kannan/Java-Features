# Database Normalization - Study Guide

## Overview

Normalization is the process of organizing data in a database to:

1. **Eliminate duplicate data** (redundancy).
2. **Ensure data consistency**.
3. **Avoid anomalies** during INSERT, UPDATE, and DELETE operations.

In real-world applications, databases are typically normalized up to **Third Normal Form (3NF)**.

---

## Example: Returns System

Suppose we are building a **retail returns application**.
A customer can create a return request for multiple items.

---

## Step 1: Unnormalized Table (UNF)

A beginner might design the table like this:

| ReturnId | CustomerName | CustomerEmail  | Product1 | Qty1 | Product2 | Qty2 | ReturnReason |
|----------|-------------|----------------|----------|------|----------|------|--------------|
| R1001    | John        | john@gmail.com | Shirt    | 1    | Jeans    | 2    | Damaged      |

### Problems

1. **Repeating Groups** — The columns `Product1`, `Qty1`, `Product2`, `Qty2` are repeating. What if the customer returns 10 products? You would need `Product3`, `Qty3`... `Product10`, `Qty10`. This is **not scalable**.

2. **Wasted Space** — If only one product is returned, lots of NULL values.

   | ReturnId | Product1 | Qty1 | Product2 | Qty2 |
   |----------|----------|------|----------|------|
   | R1002    | Shoes    | 1    | NULL     | NULL |

3. **Difficult Queries** — Finding total quantity returned for a product becomes difficult.

   ```sql
   SELECT SUM(Qty1 + Qty2 + Qty3 ...) FROM Returns;
   -- Not practical!
   ```

---

## First Normal Form (1NF)

### Rule

> Each column should contain only a **single (atomic) value**. No repeating groups.

### Solution — Convert the table:

| ReturnId | CustomerName | CustomerEmail  | Product | Qty | ReturnReason |
|----------|-------------|----------------|---------|-----|--------------|
| R1001    | John        | john@gmail.com | Shirt   | 1   | Damaged      |
| R1001    | John        | john@gmail.com | Jeans   | 2   | Damaged      |

**Now:**
- One row = One returned product
- No repeating columns
- This table is in **1NF** ✓

### Problem Still Exists

Customer information is **repeated**. If John changes his email, you must update every row. If you miss one row, data becomes **inconsistent**.

---

## Second Normal Form (2NF)

### Rule

1. Table must already be in **1NF**.
2. **No partial dependency**.

### Understanding Partial Dependency

Assume the primary key is **(ReturnId, Product)**. A row is uniquely identified by `ReturnId + Product`.

**Dependency Analysis:**

| Column        | Depends On             | Full Key? |
|---------------|------------------------|-----------|
| Qty           | ReturnId + Product     | Yes       |
| CustomerName  | ReturnId only          | No (Partial) |
| CustomerEmail | ReturnId only          | No (Partial) |
| ReturnReason  | ReturnId only          | No (Partial) |

Customer details do not depend on the **entire key**. This is called **Partial Dependency**.

### Solution — Split the table:

**Returns Table:**

| ReturnId (PK) | CustomerId | ReturnReason |
|---------------|-----------|--------------|
| R1001         | C101      | Damaged      |

**Customers Table:**

| CustomerId (PK) | CustomerName | Email          |
|-----------------|-------------|----------------|
| C101            | John        | john@gmail.com |

**ReturnItems Table:**

| ReturnId (FK) | ProductId | Qty |
|---------------|-----------|-----|
| R1001         | P100      | 1   |
| R1001         | P200      | 2   |

**Now:**
- Customer information stored **once**
- No duplicate customer data
- This is **2NF** ✓

---

## Third Normal Form (3NF)

### Rule

1. Must already be in **2NF**.
2. **Remove transitive dependencies**.

### Understanding Transitive Dependency

Suppose the Customer table has: `CustomerId`, `CustomerName`, `City`, `ZipCode`

```
CustomerId → City → ZipCode
```

- `ZipCode` does **not** directly depend on `CustomerId`
- It depends on `City`
- This is called a **Transitive Dependency**

### Problem

If Chennai's zip code changes, you must update **all** customers in Chennai. Possible inconsistency.

### Solution — Create separate City table:

**Customers Table:**

| CustomerId | CustomerName | CityId |
|-----------|-------------|--------|
| C101      | John        | CT01   |

**Cities Table:**

| CityId | CityName | ZipCode |
|--------|----------|---------|
| CT01   | Chennai  | 600001  |

**Now:**
- `CustomerId → CityId`
- `CityId → ZipCode`
- No transitive dependency
- This is **3NF** ✓

---

## Final Database Design (3NF)

```sql
CREATE TABLE Customers (
    customer_id VARCHAR(20) PRIMARY KEY,
    customer_name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE Returns (
    return_id VARCHAR(20) PRIMARY KEY,
    customer_id VARCHAR(20),
    return_date DATE,
    return_reason VARCHAR(100),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE Products (
    product_id VARCHAR(20) PRIMARY KEY,
    product_name VARCHAR(100)
);

CREATE TABLE Return_Items (
    return_id VARCHAR(20),
    product_id VARCHAR(20),
    quantity INT,
    refund_amount DECIMAL(10,2),
    PRIMARY KEY(return_id, product_id),
    FOREIGN KEY(return_id) REFERENCES Returns(return_id),
    FOREIGN KEY(product_id) REFERENCES Products(product_id)
);
```

---

## Interview Answer (2 Minutes)

> "Normalization is the process of organizing data in a database to reduce redundancy and improve data integrity. In a returns system, initially we may store customer, return, and product details in a single table, leading to duplicate customer and product information. In 1NF, we remove repeating groups and ensure atomic values. In 2NF, we remove partial dependencies by separating entities like Customers and ReturnItems. In 3NF, we remove transitive dependencies such as storing city information separately. Most OLTP systems normalize up to 3NF, although selective denormalization may be done for performance optimization."

---

## Common Interview Follow-up Questions

### Why do we normalize?

- Reduce redundancy
- Improve consistency
- Prevent anomalies
- Improve maintainability

### What are anomalies?

| # | Anomaly Type      | Description                                               |
|---|-------------------|-----------------------------------------------------------|
| 1 | **Insert Anomaly**  | Cannot insert customer without creating a return.         |
| 2 | **Update Anomaly**  | Need to update customer email in multiple rows.           |
| 3 | **Delete Anomaly**  | Deleting the last return may delete customer information. |

---

## Quick Reference

| Normal Form | Rule                                  | What it removes         |
|-------------|---------------------------------------|-------------------------|
| **1NF**     | Atomic values, no repeating groups    | Repeating columns       |
| **2NF**     | No partial dependency                 | Partial dependencies    |
| **3NF**     | No transitive dependency              | Transitive dependencies |
