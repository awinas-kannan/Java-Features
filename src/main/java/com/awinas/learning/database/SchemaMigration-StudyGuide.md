# Schema Migration - Study Guide

---

## 1. What is Schema Migration?

**Schema migration** is the process of changing the database structure in a controlled, versioned, and repeatable manner as application requirements evolve.

A schema includes:

- Tables
- Columns
- Indexes
- Constraints
- Views
- Stored Procedures
- Triggers

### Examples

```sql
-- Create a table
CREATE TABLE Customer (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100)
);

-- Add a column
ALTER TABLE Customer
ADD email VARCHAR(255);

-- Modify a column
ALTER TABLE Customer
ALTER COLUMN customer_name VARCHAR(200);

-- Drop a column
ALTER TABLE Customer
DROP COLUMN email;

-- Create an index
CREATE INDEX idx_customer_name
ON Customer(customer_name);
```

---

## 2. Why do we need Schema Migration?

- Version control for DB changes.
- Keep Dev, QA, Stage, and Prod schemas in sync.
- Automate deployments.
- Support rollbacks.
- Ensure repeatable deployments.

**Common tools:**

- Flyway
- Liquibase

---

## Senior-Level Schema Migration Questions

---

### Q1. How do you perform schema changes in production without downtime?

Use **Backward Compatible Migrations**.

**Typical approach:**

1. Add new schema objects (nullable column/new table).
2. Deploy application changes.
3. Backfill old data.
4. Switch application to new schema.
5. Remove old schema later.

**Patterns used:**

- Expand and Contract Pattern
- Blue-Green Deployment
- Rolling Deployment
- Feature Flags

---

### Q2. How do you add a new column to a production table containing 500M rows?

**Scenario**

You have:

- Spring Boot Microservice
- JPA/Hibernate
- Table already contains 500M records

**Example:**

Need to add: `customer_segment VARCHAR(50)`

---

#### User Question: Adding a new column affects the app? Will the app break?

---

#### Case 1: Add Nullable Column

```sql
ALTER TABLE orders
ADD customer_segment VARCHAR(50) NULL;
```

**Application will NOT break.**

**Reason:** JPA ignores columns not present in Entity.

```java
@Entity
class Order {

    @Id
    private Long id;

    private String status;
}
```

Extra DB column is ignored.

---

#### Case 2: Add NOT NULL Column

```sql
ALTER TABLE orders
ADD customer_segment VARCHAR(50) NOT NULL;
```

**This is dangerous.**

Old application code:

```java
Order order = new Order();
order.setStatus("NEW");

repository.save(order);
```

Generated SQL:

```sql
INSERT INTO orders(id, status)
VALUES(1, 'NEW');
```

Database throws:

> `Cannot insert NULL into customer_segment`

**Application starts failing.**

---

#### Safe Production Approach (Zero Downtime)

**Step 1: Add column as NULLABLE**

```sql
ALTER TABLE orders
ADD customer_segment VARCHAR(50) NULL;
```

Safe. Existing app continues to work.

---

**Step 2: Deploy application changes**

Update entity:

```java
@Entity
class Order {

    @Id
    private Long id;

    private String status;

    private String customerSegment;
}
```

New application version writes values.

```java
order.setCustomerSegment("PREMIUM");
```

---

**Step 3: Backfill old records**

Never do:

```sql
UPDATE orders
SET customer_segment = 'STANDARD';
```

on 500M rows.

Instead:

```sql
UPDATE orders
SET customer_segment = 'STANDARD'
WHERE id BETWEEN 1 AND 10000;
```

Run repeatedly in batches.

**Possible approaches:**

- Spring Batch
- Background Job
- Scheduler
- DB Job

**Monitor:**

- Locks
- CPU
- Transaction Log
- Replication Lag

---

**Step 4: Verify all rows updated**

```sql
SELECT COUNT(*)
FROM orders
WHERE customer_segment IS NULL;
```

Result: `0`

---

**Step 5: Add NOT NULL constraint**

```sql
ALTER TABLE orders
ALTER COLUMN customer_segment VARCHAR(50) NOT NULL;
```

Now all application instances already populate values.

---

#### Deployment Timeline

```
Release 1 — DB Migration: Add nullable column
Release 2 — Deploy application changes
Release 3 — Backfill historical data
Release 4 — Validate data
Release 5 — Make column NOT NULL
```

---

#### Important Production Considerations

**Never use**

```properties
spring.jpa.hibernate.ddl-auto=update
```

in Production.

**Prefer:**

- Flyway
- Liquibase

---

**Rolling Deployments**

During deployment:

```
Pod A -> Old Version
Pod B -> New Version
Pod C -> Old Version
```

Multiple versions coexist.

Therefore: **Schema changes must be backward compatible.**

Nullable columns are safe.

---

**Large ALTER statements**

For huge tables:

- Test migration in lower environments.
- Check lock duration.
- Perform during low traffic.
- Verify if DB supports online DDL.

---

### Q3. How do you rename a column with zero downtime?

**Bad:**

```sql
EXEC sp_rename 'orders.old_column',
               'new_column',
               'COLUMN';
```

Old application immediately breaks.

**Safe approach:**

```
Release 1 — Add new column.
Release 2 — Application writes to both columns.
Release 3 — Backfill historical data.
Release 4 — Switch reads to new column.
Release 5 — Remove old column.
```

**Release 1:**

```sql
ALTER TABLE orders
ADD new_column VARCHAR(50) NULL;
```

**Release 2:**

```java
entity.setOldColumn(value);
entity.setNewColumn(value);
```

**Release 5:**

```sql
ALTER TABLE orders
DROP COLUMN old_column;
```

---

### Q4. How do you migrate billions of rows?

**Approach:**

- Batch updates.
- Chunk by primary key.
- Parallel workers if safe.
- Background jobs.
- Avoid full table updates.

**Example:**

```sql
UPDATE orders
SET status = 'ACTIVE'
WHERE id BETWEEN 1 AND 10000;
```

Repeat.

---

### Q5. How do you create indexes on huge tables in Production?

**Example (SQL Server):**

```sql
CREATE INDEX idx_customer
ON customer(last_name)
WITH (ONLINE = ON);
```

**Prefer:**

- Online index creation.
- Low traffic windows.
- Performance monitoring.

---

### Q6. How do you handle migration rollback?

**Preferred approach:** Forward-only migrations.

```
Release 1 — Add new column.
Release 2 — Application uses new column.
Release 3 — Remove old column.
```

Avoid destructive changes immediately.

**Keep:**

- Backups
- Rollback scripts
- Feature flags

---

### Q7. What happens if Flyway migration V5 fails after V1-V4 succeeded?

- V1-V4 remain successful.
- Fix migration.
- Create new migration or repair metadata.
- **Never modify already executed production scripts.**

---

### Q8. Is Index Rebuild a Schema Migration?

**Generally: No.** Because it does not change schema.

```sql
ALTER INDEX IX_Orders_CustomerId
ON Orders
REBUILD;
```

Only the physical structure is recreated. Schema remains unchanged.

---

#### What is Index Rebuilding?

Over time, `INSERT`, `UPDATE`, `DELETE` operations fragment indexes.

**Fragmented index:**

```
Page1 -> Page8 -> Page3 -> Page15
```

Database reads become slower.

**Index rebuild:**

- Recreates entire index.
- Removes fragmentation.
- Improves performance.

```sql
ALTER INDEX IX_Orders_CustomerId
ON Orders
REBUILD;
```

**Rebuild all indexes:**

```sql
ALTER INDEX ALL
ON Orders
REBUILD;
```

---

#### Rebuild vs Reorganize

| Feature                          | Rebuild          | Reorganize       |
|----------------------------------|------------------|------------------|
| Recreates entire index           | Yes              | No               |
| Removes fragmentation completely | Yes              | Partially        |
| CPU/Disk Usage                   | High             | Low              |
| Speed                            | Faster           | Slower           |
| Suitable For                     | >30% fragmentation | 5%-30% fragmentation |

---

#### When is an Index operation considered Schema Migration?

**Maintenance (NOT Schema Migration):**

```sql
ALTER INDEX IX_Customer
ON Customer
REBUILD;
```

**Schema Migration:**

```sql
DROP INDEX IX_Customer ON Customer;

CREATE INDEX IX_Customer
ON Customer(CustomerId, OrderDate);
```

Index definition changed. Therefore, this **is** a schema migration.

---

### Strong Senior Interview Answer

> Schema migrations in production should always be backward compatible. For large tables, I avoid destructive changes and prefer an **expand-contract approach**. I first introduce additive changes such as nullable columns, deploy application changes, backfill data asynchronously in batches, validate the data, and only then enforce constraints or remove obsolete schema objects. This ensures **zero downtime**, supports **rolling deployments**, and minimizes **production risk**.
