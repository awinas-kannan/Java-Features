# Stream Intermediate Operations

Intermediate operations are **lazy** — they don't execute until a terminal operation is called.  
They always return a new `Stream<T>`, allowing method chaining.

---

## 1. filter(Predicate\<T\>)

Keeps only elements that satisfy the given condition.

```java
stream.filter(name -> name.startsWith("A"))
```

- Accepts a `Predicate<T>` which has a `test()` method returning boolean
- Supports `negate()`, `and()`, `or()` for combining predicates
- Does NOT modify elements, only includes/excludes them

| Predicate Method | Purpose |
|-----------------|---------|
| `test(T t)` | Evaluates the condition |
| `negate()` | Reverses the condition |
| `and(Predicate)` | Combines with logical AND |
| `or(Predicate)` | Combines with logical OR |

---

## 2. map(Function\<T, R\>)

Transforms each element from type `T` to type `R`.

```java
stream.map(String::toUpperCase)       // String -> String
stream.map(String::length)            // String -> Integer
```

- Accepts a `Function<T, R>` which has an `apply()` method
- Returns `Stream<R>` — the output type can differ from input
- One-to-one transformation (each input produces exactly one output)

---

## 3. flatMap(Function\<T, Stream\<R\>\>)

Flattens nested structures into a single stream.

```java
stream.flatMap(List::stream)                          // List<List<T>> -> Stream<T>
stream.flatMap(s -> Stream.of(s.split(" ")))          // Split sentences into words
```

- Each element is mapped to a Stream, then all streams are merged into one
- One-to-many transformation (each input can produce zero or more outputs)
- Essential for working with nested collections

### map vs flatMap

| Aspect | `map` | `flatMap` |
|--------|-------|-----------|
| Returns per element | Exactly one value | Zero or more values (a Stream) |
| Output structure | `Stream<Stream<R>>` if mapping to streams | `Stream<R>` (flattened) |
| Use case | Transform each element | Flatten nested collections |

```java
// map produces Stream<List<Integer>>
nestedList.stream().map(list -> list)

// flatMap produces Stream<Integer>
nestedList.stream().flatMap(List::stream)
```

---

## 4. distinct()

Removes duplicate elements using `equals()` and `hashCode()`.

```java
stream.distinct()
```

- Uses `Object.equals()` to determine duplicates
- For custom objects, you must override `equals()` and `hashCode()`
- Order is preserved (first occurrence is kept)
- Stateful operation — needs to remember previously seen elements

---

## 5. sorted() / sorted(Comparator\<T\>)

Sorts elements in natural order or by a custom comparator.

```java
stream.sorted()                                        // natural order
stream.sorted(Comparator.reverseOrder())               // descending
stream.sorted(Comparator.comparingInt(String::length)) // by property
```

- `sorted()` requires elements to implement `Comparable`
- Stateful operation — needs to see all elements before producing output
- Entire stream is buffered in memory for sorting

### sorted() vs sorted(Comparator)

| Aspect | `sorted()` | `sorted(Comparator)` |
|--------|-----------|---------------------|
| Order | Natural (Comparable) | Custom logic |
| Requirement | Elements must implement Comparable | No restriction |
| Descending | Not possible | Use `Comparator.reverseOrder()` or negate |

---

## 6. peek(Consumer\<T\>)

Performs a side-effect action on each element without modifying the stream.

```java
stream.peek(System.out::println)
      .filter(...)
      .collect(...)
```

- Accepts a `Consumer<T>` which has an `accept()` method
- Does NOT modify elements — only observes them
- Primarily used for **debugging** intermediate pipeline steps
- Does nothing without a terminal operation

### peek vs forEach

| Aspect | `peek` | `forEach` |
|--------|--------|-----------|
| Type | Intermediate | Terminal |
| Returns | Stream (allows chaining) | void (ends pipeline) |
| Purpose | Debugging/logging mid-pipeline | Final consumption |

---

## 7. limit(long n)

Truncates the stream to at most the first N elements.

```java
stream.limit(5)   // keeps first 5 elements
```

- Short-circuiting operation — may not process all elements
- Useful for infinite streams or pagination
- Preserves encounter order

---

## 8. skip(long n)

Discards the first N elements and returns the rest.

```java
stream.skip(10)   // skips first 10, keeps the rest
```

- If stream has fewer than N elements, returns empty stream
- Often combined with `limit()` for pagination

### skip + limit (Pagination Pattern)

```java
// Page 3 with page size 10
stream.skip(20).limit(10)
```

### limit vs skip

| Aspect | `limit(n)` | `skip(n)` |
|--------|-----------|-----------|
| Keeps | First N elements | Everything after first N |
| Discards | Everything after N | First N elements |
| Combined | Pagination | Pagination |

---

## 9. mapToInt(ToIntFunction\<T\>)

Converts `Stream<T>` to `IntStream` (primitive int stream).

```java
stream.mapToInt(Integer::intValue)
```

Returns an `IntStream` which provides built-in numeric methods:

| Method | Returns | Description |
|--------|---------|-------------|
| `sum()` | int | Sum of all elements |
| `min()` | OptionalInt | Minimum value |
| `max()` | OptionalInt | Maximum value |
| `average()` | OptionalDouble | Mean value |
| `summaryStatistics()` | IntSummaryStatistics | All stats in one pass |
| `count()` | long | Number of elements |

### map vs mapToInt

| Aspect | `map` | `mapToInt` |
|--------|-------|-----------|
| Returns | `Stream<Integer>` (boxed) | `IntStream` (primitive) |
| Performance | Auto-boxing/unboxing overhead | No boxing, faster |
| Memory | Object per element on heap | Raw primitive values |
| Built-in methods | None (need reduce/collect) | `sum()`, `average()`, `min()`, `max()`, `summaryStatistics()` |
| Use case | General transformation | Numeric computations |

```java
// With map — need manual reduce
int sum = stream.map(n -> n * 2).reduce(0, Integer::sum);

// With mapToInt — built-in sum()
int sum = stream.mapToInt(n -> n * 2).sum();
```

---

## 10. mapToDouble(ToDoubleFunction\<T\>)

Converts `Stream<T>` to `DoubleStream` (primitive double stream).

```java
stream.mapToDouble(Double::doubleValue)
```

- Same benefits as `mapToInt` but for double precision
- Provides `sum()`, `average()`, `min()`, `max()`, `summaryStatistics()`
- Use when working with decimal/floating point numbers

---

## 11. mapToLong(ToLongFunction\<T\>)

Converts `Stream<T>` to `LongStream` (primitive long stream).

```java
stream.mapToLong(Integer::longValue)
```

- Use when values may exceed int range (> 2.1 billion)
- Provides `sum()`, `average()`, `min()`, `max()`, `summaryStatistics()`

### mapToInt vs mapToLong vs mapToDouble

| Aspect | `mapToInt` | `mapToLong` | `mapToDouble` |
|--------|-----------|------------|--------------|
| Primitive type | int | long | double |
| Range | -2B to 2B | -9.2E18 to 9.2E18 | ~1.7E308 |
| Use case | Small whole numbers | Large whole numbers | Decimal values |
| Return stream | IntStream | LongStream | DoubleStream |

---

## 12. takeWhile(Predicate\<T\>) — Java 9+

Takes elements from the start while the condition is true. Stops at the first element that fails.

```java
stream.takeWhile(n -> n < 8)
// [5, 3] — stops at 8 because 8 >= 8
```

- Order-dependent — works best with ordered streams
- Short-circuiting — doesn't process remaining elements after first failure
- Similar to a "break" in a loop

---

## 13. dropWhile(Predicate\<T\>) — Java 9+

Drops elements from the start while the condition is true. Keeps everything after the first failure.

```java
stream.dropWhile(n -> n < 8)
// [8, 1, 9, 2, 7, 4, 6, 10, 3, 8, 1, 5] — drops until first n >= 8
```

- Opposite of `takeWhile`
- Once condition fails, ALL remaining elements are kept (even if they match the predicate)

### takeWhile vs dropWhile vs filter

| Aspect | `takeWhile` | `dropWhile` | `filter` |
|--------|------------|------------|---------|
| Keeps | Elements from start until first false | Elements after first false | All matching elements |
| Stops early | Yes | No (keeps rest) | No (checks all) |
| Order matters | Yes | Yes | No |
| Processes all elements | No | Yes | Yes |

```java
List<Integer> nums = List.of(1, 2, 3, 10, 4, 5);

takeWhile(n -> n < 10)  // [1, 2, 3] — stops at 10
dropWhile(n -> n < 10)  // [10, 4, 5] — drops until 10
filter(n -> n < 10)     // [1, 2, 3, 4, 5] — checks ALL elements
```

---

## Quick Reference Table

| Operation | Input | Returns | Stateful | Short-circuit |
|-----------|-------|---------|----------|---------------|
| `filter` | Predicate | Stream\<T\> | No | No |
| `map` | Function | Stream\<R\> | No | No |
| `flatMap` | Function (to Stream) | Stream\<R\> | No | No |
| `distinct` | — | Stream\<T\> | Yes | No |
| `sorted` | Comparator (optional) | Stream\<T\> | Yes | No |
| `peek` | Consumer | Stream\<T\> | No | No |
| `limit` | long | Stream\<T\> | Yes | Yes |
| `skip` | long | Stream\<T\> | Yes | No |
| `mapToInt` | ToIntFunction | IntStream | No | No |
| `mapToDouble` | ToDoubleFunction | DoubleStream | No | No |
| `mapToLong` | ToLongFunction | LongStream | No | No |
| `takeWhile` | Predicate | Stream\<T\> | No | Yes |
| `dropWhile` | Predicate | Stream\<T\> | No | No |

---

## Stateful vs Stateless

| Type | Operations | Impact |
|------|-----------|--------|
| **Stateless** | filter, map, flatMap, peek | Process each element independently, no memory needed |
| **Stateful** | distinct, sorted, skip, limit | Need to remember previous elements, buffer in memory |

Stateful operations can be expensive on large streams because they may need to process the entire stream before producing output (e.g., `sorted` must see all elements).
