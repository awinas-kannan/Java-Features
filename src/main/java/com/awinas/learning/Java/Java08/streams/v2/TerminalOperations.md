# Stream Terminal Operations

Terminal operations **trigger** the stream pipeline and produce a final result (value, collection, or side-effect).  
Once a terminal operation is called, the stream is **consumed** and cannot be reused.

---

## 1. forEach(Consumer\<T\>)

Performs an action on each element. Returns `void`.

```java
stream.forEach(System.out::println)
stream.forEach(e -> process(e))
```

- Accepts a `Consumer<T>` with an `accept()` method
- Does NOT guarantee order in parallel streams
- Cannot be chained (returns void)

---

## 2. forEachOrdered(Consumer\<T\>)

Same as `forEach` but guarantees encounter order even in parallel streams.

```java
stream.parallel().forEachOrdered(System.out::println)
```

### forEach vs forEachOrdered

| Aspect | `forEach` | `forEachOrdered` |
|--------|----------|-----------------|
| Order guarantee | No (in parallel) | Yes (always) |
| Performance | Faster in parallel | Slower (waits for order) |
| Sequential stream | Same behavior | Same behavior |
| Use case | When order doesn't matter | When order must be preserved |

---

## 3. collect(Collector)

Mutable reduction — accumulates elements into a container (List, Set, Map, String, etc).

### 3.1 Collectors.toList()

```java
stream.collect(Collectors.toList())
```
- Returns a mutable `ArrayList`
- Preserves encounter order

### 3.2 Collectors.toSet()

```java
stream.collect(Collectors.toSet())
```
- Returns a `HashSet` (no duplicates, no order guarantee)

### 3.3 Collectors.toMap()

```java
stream.collect(Collectors.toMap(keyMapper, valueMapper))
```
- Throws `IllegalStateException` on duplicate keys (unless merge function provided)
- Use `toMap(key, value, mergeFunction)` to handle duplicates

### 3.4 Collectors.joining()

```java
stream.collect(Collectors.joining(", "))                // delimiter only
stream.collect(Collectors.joining("::", "[", "]"))      // delimiter + prefix + suffix
```
- Concatenates elements into a single String
- Only works on `Stream<String>`

### 3.5 Collectors.groupingBy()

Groups elements by a classifier function.

```java
// Simple grouping
stream.collect(Collectors.groupingBy(String::length))

// With downstream collector
stream.collect(Collectors.groupingBy(String::length, Collectors.toSet()))

// With map factory + downstream
stream.collect(Collectors.groupingBy(String::length, HashMap::new, Collectors.toSet()))

// Composite key (multiple values)
stream.collect(Collectors.groupingBy(e -> Arrays.asList(e.getName(), e.getAge())))
```

| Overload | Parameters | Use case |
|----------|-----------|----------|
| 1-arg | classifier | Simple grouping to `Map<K, List<T>>` |
| 2-arg | classifier + downstream | Custom value collection (toSet, counting, etc.) |
| 3-arg | classifier + mapFactory + downstream | Control the Map implementation |

### 3.6 Collectors.partitioningBy()

Splits elements into exactly two groups: `true` and `false`.

```java
stream.collect(Collectors.partitioningBy(n -> n > 5))
// {true=[8, 9, 7, 6, 10, 8], false=[5, 3, 1, 2, 4, 3, 1, 5]}
```

### groupingBy vs partitioningBy

| Aspect | `groupingBy` | `partitioningBy` |
|--------|-------------|-----------------|
| Key type | Any type | Boolean only |
| Number of groups | N groups | Exactly 2 (true/false) |
| Use case | Categorize by property | Binary split |

### 3.7 Collectors.counting()

Counts elements (used as downstream collector).

```java
stream.collect(Collectors.groupingBy(String::length, Collectors.counting()))
```

### 3.8 Collectors.summarizingDouble/Int/Long()

Produces statistics in one pass.

```java
stream.collect(Collectors.summarizingDouble(Double::doubleValue))
// DoubleSummaryStatistics{count=10, sum=899.99, min=25.10, average=89.99, max=200.00}
```

### toList vs toSet vs toMap

| Aspect | `toList` | `toSet` | `toMap` |
|--------|---------|---------|---------|
| Duplicates | Allowed | Removed | Throws (unless merge fn) |
| Order | Preserved | Not guaranteed | Not guaranteed |
| Returns | ArrayList | HashSet | HashMap |
| Use case | General collection | Unique elements | Key-value lookup |

---

## 4. reduce()

Combines all elements into a single result using an accumulator function.

### 4.1 reduce(BinaryOperator) — No identity

```java
Optional<Integer> sum = stream.reduce(Integer::sum);
Optional<String> concat = stream.reduce((a, b) -> a + "--" + b);
```
- Returns `Optional<T>` because stream might be empty
- No starting value

### 4.2 reduce(identity, BinaryOperator) — With identity

```java
int sum = stream.reduce(0, Integer::sum);         // identity = 0
int product = stream.reduce(1, (a, b) -> a * b);  // identity = 1
String concat = stream.reduce("Start:", (a, b) -> a + " " + b);
```
- Returns `T` directly (not Optional), because identity is the default
- Identity value is both the initial value and the default for empty streams

### 4.3 reduce(identity, BiFunction, BinaryOperator) — 3-arg with combiner

```java
int totalLength = names.stream()
    .reduce(0,
            (total, name) -> total + name.length(),   // accumulator: Integer + String -> Integer
            Integer::sum);                             // combiner: Integer + Integer -> Integer
```
- Used when **accumulator type differs from stream element type**
- Combiner merges partial results (required for parallel streams)
- In sequential streams, combiner is not actually called but still required

### reduce: 1-arg vs 2-arg vs 3-arg

| Variant | Parameters | Returns | When to use |
|---------|-----------|---------|-------------|
| 1-arg | BinaryOperator | `Optional<T>` | Simple reduction, same type |
| 2-arg | identity + BinaryOperator | `T` | Need a default/start value, same type |
| 3-arg | identity + BiFunction + BinaryOperator | `U` | Result type differs from element type |

### reduce vs collect

| Aspect | `reduce` | `collect` |
|--------|---------|-----------|
| Result | Immutable single value | Mutable container |
| How | Combines using function | Accumulates into container |
| Performance | Creates new objects each step | Mutates single container |
| Use case | Sum, product, concatenation | Lists, Sets, Maps, Strings |

```java
// reduce creates new String objects on each concatenation (O(n²))
stream.reduce("", (a, b) -> a + b)

// collect mutates StringBuilder internally (O(n))
stream.collect(Collectors.joining())
```

### reduce with parallelStream

```java
// Combiner is used to merge partial results from parallel threads
int sum = numbers.parallelStream().reduce(0, Integer::sum);
```

- In parallel, stream is split into chunks
- Each chunk is reduced independently
- Combiner merges the chunk results
- Identity must be an identity value for the combiner (e.g., 0 for sum, 1 for product)

---

## 5. count()

Returns the total number of elements as `long`.

```java
long total = stream.count();
long filtered = stream.filter(predicate).count();
```

- Simple terminal operation
- Often combined with `filter` to count matching elements

---

## 6. min(Comparator\<T\>)

Finds the smallest element. Returns `Optional<T>`.

```java
Optional<Integer> min = stream.min(Integer::compareTo);
Optional<String> min = stream.min(Comparator.comparingInt(String::length));
```

---

## 7. max(Comparator\<T\>)

Finds the largest element. Returns `Optional<T>`.

```java
Optional<Integer> max = stream.max(Integer::compareTo);
```

### min vs max

| Aspect | `min` | `max` |
|--------|-------|-------|
| Returns | Smallest by comparator | Largest by comparator |
| Empty stream | `Optional.empty()` | `Optional.empty()` |
| Both need | Comparator | Comparator |

---

## 8. findFirst()

Returns the first element of the stream. Returns `Optional<T>`.

```java
Optional<String> first = stream.filter(s -> s.startsWith("A")).findFirst();
```

- Deterministic — always returns the same element for ordered streams
- Short-circuiting — stops processing once found

---

## 9. findAny()

Returns any matching element. Returns `Optional<T>`.

```java
Optional<String> any = stream.parallelStream().filter(s -> s.length() > 3).findAny();
```

### findFirst vs findAny

| Aspect | `findFirst` | `findAny` |
|--------|------------|-----------|
| Result | Always first element | Any element (non-deterministic in parallel) |
| Sequential stream | Same behavior | Same behavior |
| Parallel stream | Deterministic (slower) | Non-deterministic (faster) |
| Use case | Need stable/predictable result | Just need existence check, performance matters |

---

## 10. anyMatch(Predicate\<T\>)

Returns `true` if **at least one** element matches.

```java
boolean hasLarge = stream.anyMatch(n -> n > 100);
```

- Short-circuiting — stops at first match

---

## 11. allMatch(Predicate\<T\>)

Returns `true` only if **ALL** elements match.

```java
boolean allPositive = stream.allMatch(n -> n > 0);
```

- Short-circuiting — stops at first non-match
- Returns `true` for empty stream

---

## 12. noneMatch(Predicate\<T\>)

Returns `true` if **NO** elements match.

```java
boolean noNegatives = stream.noneMatch(n -> n < 0);
```

- Short-circuiting — stops at first match
- Returns `true` for empty stream

### anyMatch vs allMatch vs noneMatch

| Operation | Returns true when | Short-circuits on | Empty stream returns |
|-----------|------------------|-------------------|---------------------|
| `anyMatch` | At least one matches | First match | `false` |
| `allMatch` | All elements match | First non-match | `true` |
| `noneMatch` | No elements match | First match | `true` |

```java
// Logical equivalences
anyMatch(p)   == !noneMatch(p)
allMatch(p)   == noneMatch(p.negate())
noneMatch(p)  == !anyMatch(p)
```

---

## 13. toArray()

Converts stream into an array.

```java
Object[] arr = stream.toArray();                    // returns Object[]
String[] arr = stream.toArray(String[]::new);       // returns typed String[]
```

### toArray() vs toArray(generator)

| Aspect | `toArray()` | `toArray(IntFunction)` |
|--------|------------|----------------------|
| Returns | `Object[]` | `T[]` (typed array) |
| Type safety | Needs casting | Type-safe |
| Use case | Quick conversion | When you need typed array |

---

## Quick Reference Table

| Operation | Input | Returns | Short-circuit |
|-----------|-------|---------|---------------|
| `forEach` | Consumer | void | No |
| `forEachOrdered` | Consumer | void | No |
| `collect` | Collector | R (container) | No |
| `reduce` | BinaryOperator | Optional\<T\> or T | No |
| `count` | — | long | No |
| `min` | Comparator | Optional\<T\> | No |
| `max` | Comparator | Optional\<T\> | No |
| `findFirst` | — | Optional\<T\> | Yes |
| `findAny` | — | Optional\<T\> | Yes |
| `anyMatch` | Predicate | boolean | Yes |
| `allMatch` | Predicate | boolean | Yes |
| `noneMatch` | Predicate | boolean | Yes |
| `toArray` | — | Object[] or T[] | No |

---

## Short-circuiting Terminal Operations

These operations can terminate early without processing the entire stream:

| Operation | Stops when |
|-----------|-----------|
| `findFirst` | First element found |
| `findAny` | Any element found |
| `anyMatch` | First element matches |
| `allMatch` | First element doesn't match |
| `noneMatch` | First element matches |

Non-short-circuiting operations (`forEach`, `collect`, `reduce`, `count`, `min`, `max`, `toArray`) must process every element.
