package com.awinas.learning.Java.Java08.functionalinterfaces;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * FUNCTION<T, R> -- The Transformer / Converter
 *
 * Signature : R apply(T t)
 * Input     : One value of type T
 * Output    : One value of type R
 *
 * WHY IT EXISTS:
 * - Before Java 8, converting/transforming data required loops and temp variables.
 * - Function lets you pass "transformation logic" as a parameter.
 * - It powers Stream.map(), Optional.map(), Map.computeIfAbsent(), etc.
 *
 * REAL-WORLD ANALOGY:
 * Think of a currency converter -- you give it INR (input), it gives you USD (output).
 * The conversion logic itself is the Function.
 *
 * DEFAULT METHODS:
 * - andThen(Function) --> applies this function first, then the other
 * - compose(Function) --> applies the other function first, then this
 *
 * STATIC METHOD:
 * - Function.identity() --> returns the input as-is (x -> x)
 */
public class FunctionExample {

    // =============================================================
    // STEP 1: UNDERSTAND THE SOURCE -- What does Function look like internally?
    //
    //   @FunctionalInterface
    //   public interface Function<T, R> {
    //       R apply(T t);      <-- THE one abstract method
    //   }
    //
    //   T = input type, R = return type.
    //   It takes ONE input of type T and returns ONE output of type R.
    //   Any logic that converts/transforms data is a Function.
    // =============================================================

    // =============================================================
    // STEP 2: Create your OWN functional interface (to understand the concept)
    //
    //   @FunctionalInterface
    //   interface MyFunction<T, R> {
    //       R apply(T t);
    //   }
    //
    //   This is exactly what java.util.function.Function is.
    // =============================================================

    // =============================================================
    // STEP 3: IMPLEMENT using a concrete class (the OLD Java way)
    // =============================================================
    static class UpperCaseConverter implements Function<String, String> {
        @Override
        public String apply(String input) {
            return input.toUpperCase();
        }
    }

    static class StringLengthCalculator implements Function<String, Integer> {
        @Override
        public Integer apply(String input) {
            return input.length();
        }
    }

    public static void main(String[] args) {

        // =============================================================
        // STEP 4: USE the concrete class -- works but too much boilerplate
        // =============================================================
        Function<String, String> converter1 = new UpperCaseConverter();
        System.out.println("Using class: " + converter1.apply("awinas"));  // AWINAS

        Function<String, Integer> lengthCalc = new StringLengthCalculator();
        System.out.println("Using class: " + lengthCalc.apply("awinas")); // 6

        // =============================================================
        // STEP 5: ANONYMOUS CLASS -- shorter, but still verbose
        // =============================================================
        Function<String, String> converter2 = new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input.toUpperCase();
            }
        };
        System.out.println("Using anonymous: " + converter2.apply("awinas")); // AWINAS

        // =============================================================
        // STEP 6: LAMBDA -- Since Function has only ONE abstract method (apply),
        //         the compiler knows the lambda IS the apply() method.
        // =============================================================
        Function<String, String> converter3 = input -> input.toUpperCase();
        System.out.println("Using lambda: " + converter3.apply("awinas")); // AWINAS

        // =============================================================
        // STEP 7: METHOD REFERENCE -- Even shorter when a method already exists
        //         String::toUpperCase is shorthand for (s) -> s.toUpperCase()
        // =============================================================
        Function<String, String> converter4 = String::toUpperCase;
        System.out.println("Using method ref: " + converter4.apply("awinas")); // AWINAS

        // ALL FOUR (class, anonymous, lambda, method ref) do the EXACT same thing.

        System.out.println("\n========================================");
        System.out.println("  REAL-WORLD EXAMPLES");
        System.out.println("========================================\n");

        // -------------------------------------------------------
        // 1. BASIC: Convert product name to uppercase
        // -------------------------------------------------------
        Function<String, String> toUpperCase = String::toUpperCase;
        System.out.println(toUpperCase.apply("iphone 15"));
        // IPHONE 15

        // -------------------------------------------------------
        // 2. TRANSFORM TYPE: String to its length
        // -------------------------------------------------------
        Function<String, Integer> getLength = String::length;
        System.out.println("Length of 'Awinas': " + getLength.apply("Awinas"));
        // 6

        // -------------------------------------------------------
        // 3. REAL-WORLD: Extract order total from order string
        //    Simulating: "ORD-1234:599.99" -> 599.99
        // -------------------------------------------------------
        Function<String, Double> extractAmount = order -> {
            String[] parts = order.split(":");
            return Double.parseDouble(parts[1]);
        };

        List<String> orders = Arrays.asList(
                "ORD-1001:250.00", "ORD-1002:1500.50",
                "ORD-1003:89.99", "ORD-1004:3200.00"
        );

        double totalRevenue = orders.stream()
                .map(extractAmount)
                .reduce(0.0, Double::sum);
        System.out.println("Total revenue: ₹" + totalRevenue);
        // Total revenue: ₹5040.49

        // -------------------------------------------------------
        // 4. andThen: Chain transformations (this FIRST, then next)
        // -------------------------------------------------------
        Function<String, String> trim = String::trim;
        Function<String, String> toLower = String::toLowerCase;

        Function<String, String> cleanInput = trim.andThen(toLower);
        System.out.println(cleanInput.apply("  AWINAS KANNAN  "));
        // awinas kannan

        // -------------------------------------------------------
        // 5. compose: Chain transformations (other FIRST, then this)
        //    compose is the reverse of andThen
        // -------------------------------------------------------
        Function<String, String> addGreeting = name -> "Hello, " + name + "!";
        Function<String, String> greetCleanName = addGreeting.compose(cleanInput);

        System.out.println(greetCleanName.apply("  AWINAS  "));
        // Hello, awinas!

        // -------------------------------------------------------
        // 6. Function.identity(): Returns input as-is
        //    Useful in collectors when you need the element itself as key/value
        // -------------------------------------------------------
        List<String> fruits = Arrays.asList("apple", "banana", "apple", "cherry", "banana", "apple");

        Map<String, Long> fruitCount = fruits.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Fruit count: " + fruitCount);
        // {banana=2, cherry=1, apple=3}

        // -------------------------------------------------------
        // 7. REAL-WORLD: Map.computeIfAbsent with Function
        //    Build a cache -- compute value only if key is missing
        // -------------------------------------------------------
        Map<String, Integer> priceCache = new HashMap<>();
        Function<String, Integer> fetchPrice = product -> {
            System.out.println("  Fetching price for " + product + "...");
            switch (product) {
                case "iPhone": return 79999;
                case "Samsung": return 59999;
                default: return 9999;
            }
        };

        System.out.println("\nFirst call (cache miss):");
        int price1 = priceCache.computeIfAbsent("iPhone", fetchPrice);
        System.out.println("Price: ₹" + price1);

        System.out.println("Second call (cache hit -- no fetch):");
        int price2 = priceCache.computeIfAbsent("iPhone", fetchPrice);
        System.out.println("Price: ₹" + price2);

        // -------------------------------------------------------
        // 8. REAL-WORLD: Pipeline of transformations using Stream.map()
        // -------------------------------------------------------
        List<String> rawEmails = Arrays.asList(
                "  AWINAS@Gmail.COM  ", "Kumar@Yahoo.COM", "  JOHN@outlook.COM"
        );

        List<String> cleanEmails = rawEmails.stream()
                .map(trim)
                .map(toLower)
                .collect(Collectors.toList());
        System.out.println("\nCleaned emails: " + cleanEmails);
        // [awinas@gmail.com, kumar@yahoo.com, john@outlook.com]

        // -------------------------------------------------------
        // 9. Passing Function as method parameter
        // -------------------------------------------------------
        List<String> names = Arrays.asList("awinas", "kumar", "john");
        System.out.println("\nTransformed:");
        printTransformed(names, name -> name.substring(0, 1).toUpperCase() + name.substring(1));
        // Awinas, Kumar, John
    }

    static <T, R> void printTransformed(List<T> list, Function<T, R> transformer) {
        list.stream()
                .map(transformer)
                .forEach(item -> System.out.println("  -> " + item));
    }
}
