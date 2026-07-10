package com.awinas.learning.Java.Java08.functionalinterfaces;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * BIFUNCTION<T, U, R> -- The Combiner / Merger
 *
 * Signature : R apply(T t, U u)
 * Input     : TWO values -- one of type T, one of type U
 * Output    : ONE value of type R
 *
 * WHY IT EXISTS:
 * - Function<T,R> takes only ONE input. Sometimes you need TWO inputs
 *   to produce a result (e.g., combine first name + last name, calculate tax
 *   from amount + rate, merge two map values).
 * - BiFunction is the two-argument version of Function.
 * - It powers Map.merge(), Map.replaceAll(), Map.compute(), etc.
 *
 * REAL-WORLD ANALOGY:
 * Think of a restaurant bill calculator:
 *   Input 1  = food total (₹1000)
 *   Input 2  = tip percentage (10%)
 *   Output   = final bill (₹1100)
 * Two inputs, one output = BiFunction.
 *
 * DEFAULT METHOD:
 * - andThen(Function) --> applies BiFunction first, then Function on the result
 *   (Note: there is NO compose(), because you can't feed one result into two params)
 *
 * RELATIONSHIP:
 * - BiFunction<T, U, R>  --> two different input types, one output type
 * - BinaryOperator<T>    --> special case where all three types are the SAME
 *                            (extends BiFunction<T, T, T>)
 */
public class BiFunctionExample {

    // =============================================================
    // STEP 1: UNDERSTAND THE SOURCE -- What does BiFunction look like internally?
    //
    //   @FunctionalInterface
    //   public interface BiFunction<T, U, R> {
    //       R apply(T t, U u);      <-- THE one abstract method
    //   }
    //
    //   T = first input type
    //   U = second input type
    //   R = return type
    //
    //   It takes TWO inputs and returns ONE output.
    // =============================================================

    // =============================================================
    // STEP 2: Create your OWN functional interface (to understand the concept)
    //
    //   @FunctionalInterface
    //   interface MyBiFunction<T, U, R> {
    //       R apply(T t, U u);
    //   }
    //
    //   This is exactly what java.util.function.BiFunction is.
    // =============================================================

    // =============================================================
    // STEP 3: IMPLEMENT using a concrete class (the OLD Java way)
    // =============================================================
    static class FullNameCombiner implements BiFunction<String, String, String> {
        @Override
        public String apply(String firstName, String lastName) {
            return firstName + " " + lastName;
        }
    }

    static class BillCalculator implements BiFunction<Double, Double, Double> {
        @Override
        public Double apply(Double amount, Double taxPercentage) {
            return amount + (amount * taxPercentage / 100);
        }
    }

    public static void main(String[] args) {

        // =============================================================
        // STEP 4: USE the concrete class -- works but too much boilerplate
        // =============================================================
        BiFunction<String, String, String> combiner1 = new FullNameCombiner();
        System.out.println("Using class: " + combiner1.apply("Awinas", "Kannan"));
        // Awinas Kannan

        BiFunction<Double, Double, Double> calculator1 = new BillCalculator();
        System.out.println("Using class: ₹" + calculator1.apply(1000.0, 18.0));
        // ₹1180.0

        // =============================================================
        // STEP 5: ANONYMOUS CLASS -- no separate class needed, but verbose
        // =============================================================
        BiFunction<String, String, String> combiner2 = new BiFunction<String, String, String>() {
            @Override
            public String apply(String firstName, String lastName) {
                return firstName + " " + lastName;
            }
        };
        System.out.println("Using anonymous: " + combiner2.apply("Awinas", "Kannan"));

        // =============================================================
        // STEP 6: LAMBDA -- Since BiFunction has only ONE abstract method (apply),
        //         the compiler knows the lambda IS the apply() method.
        //         Note: TWO parameters in the lambda --> (param1, param2) ->
        // =============================================================
        BiFunction<String, String, String> combiner3 = (first, last) -> first + " " + last;
        System.out.println("Using lambda: " + combiner3.apply("Awinas", "Kannan"));

        // ALL THREE (class, anonymous, lambda) do the EXACT same thing.

        System.out.println("\n========================================");
        System.out.println("  REAL-WORLD EXAMPLES");
        System.out.println("========================================\n");

        // -------------------------------------------------------
        // 1. REAL-WORLD: Bill calculator with tax
        // -------------------------------------------------------
        BiFunction<Double, Double, Double> calculateBill =
                (amount, taxPercent) -> amount + (amount * taxPercent / 100);

        System.out.println("Food: ₹1000, GST 18% = ₹" + calculateBill.apply(1000.0, 18.0));
        System.out.println("Food: ₹500,  GST 5%  = ₹" + calculateBill.apply(500.0, 5.0));

        // -------------------------------------------------------
        // 2. REAL-WORLD: Salary calculator (base + experience bonus)
        // -------------------------------------------------------
        BiFunction<Double, Integer, Double> calculateSalary =
                (baseSalary, yearsOfExp) -> baseSalary + (yearsOfExp * 5000.0);

        System.out.println("\nSalary (base 50k, 5 yrs): ₹" + calculateSalary.apply(50000.0, 5));
        System.out.println("Salary (base 50k, 10 yrs): ₹" + calculateSalary.apply(50000.0, 10));

        // -------------------------------------------------------
        // 3. andThen: Chain BiFunction with a Function
        //    Calculate bill, THEN format it as a string
        // -------------------------------------------------------
        BiFunction<Double, Double, String> formattedBill = calculateBill
                .andThen(total -> String.format("₹%.2f", total));

        System.out.println("\nFormatted: " + formattedBill.apply(1000.0, 18.0));
        // ₹1180.00

        // -------------------------------------------------------
        // 4. REAL-WORLD: Map.merge() -- THE most important use of BiFunction
        //    When two values have the same key, BiFunction decides what to do
        // -------------------------------------------------------
        Map<String, Integer> salesJanuary = new HashMap<>();
        salesJanuary.put("iPhone", 100);
        salesJanuary.put("Samsung", 80);
        salesJanuary.put("OnePlus", 60);

        Map<String, Integer> salesFebruary = new HashMap<>();
        salesFebruary.put("iPhone", 120);
        salesFebruary.put("Samsung", 90);
        salesFebruary.put("Pixel", 40);

        Map<String, Integer> totalSales = new HashMap<>(salesJanuary);

        BiFunction<Integer, Integer, Integer> mergeBySum = Integer::sum;

        salesFebruary.forEach((product, count) ->
                totalSales.merge(product, count, mergeBySum));

        System.out.println("\n--- Merged Sales ---");
        totalSales.forEach((product, count) ->
                System.out.println("  " + product + ": " + count + " units"));
        // iPhone: 220, Samsung: 170, OnePlus: 60, Pixel: 40

        // -------------------------------------------------------
        // 5. REAL-WORLD: Map.compute() -- compute new value from key + old value
        // -------------------------------------------------------
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("iPhone", 50);
        inventory.put("Samsung", 30);

        BiFunction<String, Integer, Integer> restock =
                (product, currentStock) -> currentStock == null ? 100 : currentStock + 100;

        inventory.compute("iPhone", restock);     // 50 + 100 = 150
        inventory.compute("Samsung", restock);    // 30 + 100 = 130
        inventory.compute("OnePlus", restock);    // null -> 100 (new product)

        System.out.println("\n--- After Restocking ---");
        inventory.forEach((product, stock) ->
                System.out.println("  " + product + ": " + stock));

        // -------------------------------------------------------
        // 6. REAL-WORLD: Map.replaceAll() -- transform all values using key + value
        // -------------------------------------------------------
        Map<String, Double> prices = new HashMap<>();
        prices.put("iPhone", 79999.0);
        prices.put("Samsung", 59999.0);
        prices.put("OnePlus", 39999.0);

        System.out.println("\n--- Before Discount ---");
        prices.forEach((p, price) -> System.out.println("  " + p + ": ₹" + price));

        prices.replaceAll((product, price) -> {
            if (product.equals("OnePlus")) return price * 0.80;  // 20% off
            return price * 0.90;  // 10% off for others
        });

        System.out.println("--- After Discount ---");
        prices.forEach((p, price) -> System.out.println("  " + p + ": ₹" + String.format("%.2f", price)));

        // -------------------------------------------------------
        // 7. Different input types, different output type
        //    (String, Integer) -> List<String>
        // -------------------------------------------------------
        BiFunction<String, Integer, List<String>> repeat =
                (text, times) -> {
                    List<String> result = new java.util.ArrayList<>();
                    for (int i = 0; i < times; i++) {
                        result.add(text);
                    }
                    return result;
                };

        System.out.println("\nRepeat 'Hello' 3 times: " + repeat.apply("Hello", 3));
        // [Hello, Hello, Hello]

        // -------------------------------------------------------
        // 8. Passing BiFunction as method parameter
        // -------------------------------------------------------
        System.out.println("\n--- Custom Operations ---");
        System.out.println("Add:      " + operate(10, 20, (a, b) -> a + b));
        System.out.println("Multiply: " + operate(10, 20, (a, b) -> a * b));
        System.out.println("Max:      " + operate(10, 20, Math::max));
        System.out.println("Power:    " + operate(2, 8, (a, b) -> (int) Math.pow(a, b)));
    }

    static <T, U, R> R operate(T input1, U input2, BiFunction<T, U, R> operation) {
        return operation.apply(input1, input2);
    }
}
