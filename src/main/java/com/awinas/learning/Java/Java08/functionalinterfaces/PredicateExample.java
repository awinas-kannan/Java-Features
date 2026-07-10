package com.awinas.learning.Java.Java08.functionalinterfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * PREDICATE<T> -- The Tester / Filter
 *
 * Signature : boolean test(T t)
 * Input     : One value of type T
 * Output    : boolean (true / false)
 *
 * WHY IT EXISTS:
 * - Before Java 8, filtering a list required writing loops with if-conditions.
 * - Predicate lets you pass the "condition" itself as a parameter.
 * - It powers Stream.filter(), Optional.filter(), Collection.removeIf(), etc.
 *
 * REAL-WORLD ANALOGY:
 * Think of airport security -- they have a checklist (predicate) and every
 * passenger is tested against it. Pass = board, Fail = rejected.
 *
 * DEFAULT METHODS:
 * - and(Predicate)  --> logical AND (both must be true)
 * - or(Predicate)   --> logical OR  (either can be true)
 * - negate()        --> logical NOT (reverse the result)
 *
 * STATIC METHOD:
 * - Predicate.isEqual(target) --> checks equality with target
 */
public class PredicateExample {

    // =============================================================
    // STEP 1: UNDERSTAND THE SOURCE -- What does Predicate look like internally?
    //
    //   @FunctionalInterface
    //   public interface Predicate<T> {
    //       boolean test(T t);      <-- THE one abstract method
    //   }
    //
    //   It takes ONE input and returns a BOOLEAN.
    //   That's it. Any logic that answers "yes or no" is a Predicate.
    // =============================================================

    // =============================================================
    // STEP 2: Create your OWN functional interface (to understand the concept)
    //
    //   @FunctionalInterface
    //   interface MyPredicate<T> {
    //       boolean test(T t);
    //   }
    //
    //   This is exactly what java.util.function.Predicate is.
    //   Java just provides it for you so you don't write it yourself.
    // =============================================================

    // =============================================================
    // STEP 3: IMPLEMENT using a concrete class (the OLD Java way)
    // =============================================================
    static class SamsungChecker implements Predicate<String> {
        @Override
        public boolean test(String product) {
            return product.startsWith("Samsung");
        }
    }

    public static void main(String[] args) {

        List<String> products = Arrays.asList(
                "iPhone 15", "Samsung Galaxy S24", "OnePlus 12",
                "iPad Pro", "MacBook Air", "Samsung TV", "OnePlus Buds"
        );

        // =============================================================
        // STEP 4: USE the concrete class -- works but too much boilerplate
        // =============================================================
        Predicate<String> checker1 = new SamsungChecker();
        System.out.println("Using class: " + checker1.test("Samsung Galaxy S24")); // true
        System.out.println("Using class: " + checker1.test("iPhone 15"));          // false

        // =============================================================
        // STEP 5: ANONYMOUS CLASS -- no need to create a separate class
        //         but still verbose
        // =============================================================
        Predicate<String> checker2 = new Predicate<String>() {
            @Override
            public boolean test(String product) {
                return product.startsWith("Samsung");
            }
        };
        System.out.println("Using anonymous: " + checker2.test("Samsung TV")); // true

        // =============================================================
        // STEP 6: LAMBDA -- Since Predicate has only ONE abstract method,
        //         the compiler knows which method the lambda implements.
        //         This is the WHOLE REASON functional interfaces exist.
        // =============================================================
        Predicate<String> checker3 = product -> product.startsWith("Samsung");
        System.out.println("Using lambda: " + checker3.test("Samsung TV")); // true

        // ALL THREE (class, anonymous, lambda) do the EXACT same thing.
        // Lambda is just syntactic sugar for the anonymous class.

        System.out.println("\n========================================");
        System.out.println("  REAL-WORLD EXAMPLES");
        System.out.println("========================================\n");

        // -------------------------------------------------------
        // 1. BASIC: Filter products that start with "Samsung"
        // -------------------------------------------------------
        Predicate<String> isSamsung = product -> product.startsWith("Samsung");

        List<String> samsungProducts = products.stream()
                .filter(isSamsung)
                .collect(Collectors.toList());
        System.out.println("Samsung products: " + samsungProducts);
        // [Samsung Galaxy S24, Samsung TV]

        // -------------------------------------------------------
        // 2. AND: Samsung products that contain "Galaxy"
        // -------------------------------------------------------
        Predicate<String> containsGalaxy = product -> product.contains("Galaxy");

        List<String> samsungPhones = products.stream()
                .filter(isSamsung.and(containsGalaxy))
                .collect(Collectors.toList());
        System.out.println("Samsung phones: " + samsungPhones);
        // [Samsung Galaxy S24]

        // -------------------------------------------------------
        // 3. OR: Samsung OR OnePlus products
        // -------------------------------------------------------
        Predicate<String> isOnePlus = product -> product.startsWith("OnePlus");

        List<String> samsungOrOnePlus = products.stream()
                .filter(isSamsung.or(isOnePlus))
                .collect(Collectors.toList());
        System.out.println("Samsung or OnePlus: " + samsungOrOnePlus);
        // [Samsung Galaxy S24, OnePlus 12, Samsung TV, OnePlus Buds]

        // -------------------------------------------------------
        // 4. NEGATE: Everything that is NOT Apple
        // -------------------------------------------------------
        Predicate<String> isApple = product ->
                product.startsWith("iPhone") || product.startsWith("iPad") || product.startsWith("Mac");

        List<String> nonApple = products.stream()
                .filter(isApple.negate())
                .collect(Collectors.toList());
        System.out.println("Non-Apple products: " + nonApple);
        // [Samsung Galaxy S24, OnePlus 12, Samsung TV, OnePlus Buds]

        // -------------------------------------------------------
        // 5. Predicate.isEqual: Check if a specific product exists
        // -------------------------------------------------------
        Predicate<String> isIPhone15 = Predicate.isEqual("iPhone 15");
        System.out.println("Is iPhone 15 in list? " +
                products.stream().anyMatch(isIPhone15));
        // true

        // -------------------------------------------------------
        // 6. REAL-WORLD: Reusable validation predicates
        // -------------------------------------------------------
        Predicate<String> isNotEmpty = s -> !s.isEmpty();
        Predicate<String> isValidLength = s -> s.length() >= 3 && s.length() <= 50;
        Predicate<String> noSpecialChars = s -> s.matches("[a-zA-Z0-9 ]+");

        Predicate<String> isValidProductName = isNotEmpty
                .and(isValidLength)
                .and(noSpecialChars);

        System.out.println("'iPhone 15' valid? " + isValidProductName.test("iPhone 15"));   // true
        System.out.println("'' valid? " + isValidProductName.test(""));                       // false
        System.out.println("'AB' valid? " + isValidProductName.test("AB"));                   // false

        // -------------------------------------------------------
        // 7. REAL-WORLD: removeIf -- modify collection in place
        // -------------------------------------------------------
        List<Integer> orderAmounts = new java.util.ArrayList<>(
                Arrays.asList(50, 200, 15, 500, 8, 1000, 30)
        );
        orderAmounts.removeIf(amount -> amount < 20);
        System.out.println("Orders above minimum: " + orderAmounts);
        // [50, 200, 500, 1000, 30]

        // -------------------------------------------------------
        // 8. Passing Predicate as method parameter
        // -------------------------------------------------------
        System.out.println("\nProducts with length > 10:");
        printFiltered(products, p -> p.length() > 10);
    }

    static <T> void printFiltered(List<T> list, Predicate<T> condition) {
        list.stream()
                .filter(condition)
                .forEach(item -> System.out.println("  -> " + item));
    }
}
