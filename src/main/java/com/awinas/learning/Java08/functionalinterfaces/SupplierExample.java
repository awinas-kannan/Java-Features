package com.awinas.learning.Java08.functionalinterfaces;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

/**
 * SUPPLIER<T> -- The Factory / Producer
 *
 * Signature : T get()
 * Input     : Nothing
 * Output    : One value of type T
 *
 * WHY IT EXISTS:
 * - Sometimes you need to provide a value LAZILY -- only create it when needed.
 * - Supplier delays the creation/computation until .get() is called.
 * - It powers Optional.orElseGet(), Logger lazy messages, factory patterns, etc.
 *
 * REAL-WORLD ANALOGY:
 * Think of a vending machine -- it doesn't make the drink until you press
 * the button. The "recipe" is stored (Supplier), the "drink" is produced
 * only when you call get().
 *
 * KEY BENEFIT: LAZY EVALUATION
 * - orElse(expensiveCall())        --> always executes, even if Optional has value
 * - orElseGet(() -> expensiveCall()) --> executes ONLY if Optional is empty
 */
public class SupplierExample {

    // =============================================================
    // STEP 1: UNDERSTAND THE SOURCE -- What does Supplier look like internally?
    //
    //   @FunctionalInterface
    //   public interface Supplier<T> {
    //       T get();      <-- THE one abstract method
    //   }
    //
    //   It takes NO input and returns ONE output of type T.
    //   Any logic that "produces/creates/generates" a value is a Supplier.
    //   Think: factory, generator, lazy initializer.
    // =============================================================

    // =============================================================
    // STEP 2: Create your OWN functional interface (to understand the concept)
    //
    //   @FunctionalInterface
    //   interface MySupplier<T> {
    //       T get();
    //   }
    //
    //   This is exactly what java.util.function.Supplier is.
    // =============================================================

    // =============================================================
    // STEP 3: IMPLEMENT using a concrete class (the OLD Java way)
    // =============================================================
    static class DefaultNameSupplier implements Supplier<String> {
        @Override
        public String get() {
            return "Guest User";
        }
    }

    static class OrderIdSupplier implements Supplier<String> {
        private int counter = 1000;

        @Override
        public String get() {
            return "ORD-" + (++counter);
        }
    }

    public static void main(String[] args) {

        // =============================================================
        // STEP 4: USE the concrete class -- works but too much boilerplate
        // =============================================================
        Supplier<String> nameSupplier1 = new DefaultNameSupplier();
        System.out.println("Using class: " + nameSupplier1.get()); // Guest User

        Supplier<String> orderSupplier = new OrderIdSupplier();
        System.out.println("Using class: " + orderSupplier.get()); // ORD-1001
        System.out.println("Using class: " + orderSupplier.get()); // ORD-1002

        // =============================================================
        // STEP 5: ANONYMOUS CLASS -- no separate class needed, but verbose
        // =============================================================
        Supplier<String> nameSupplier2 = new Supplier<String>() {
            @Override
            public String get() {
                return "Guest User";
            }
        };
        System.out.println("Using anonymous: " + nameSupplier2.get()); // Guest User

        // =============================================================
        // STEP 6: LAMBDA -- Since Supplier has only ONE abstract method (get),
        //         the compiler knows the lambda IS the get() method.
        //         Note: no parameters because get() takes nothing --> () ->
        // =============================================================
        Supplier<String> nameSupplier3 = () -> "Guest User";
        System.out.println("Using lambda: " + nameSupplier3.get()); // Guest User

        // =============================================================
        // STEP 7: METHOD REFERENCE -- When a no-arg method already exists
        //         Math::random is shorthand for () -> Math.random()
        // =============================================================
        Supplier<Double> randomSupplier = Math::random;
        System.out.println("Using method ref: " + randomSupplier.get());

        // ALL FOUR (class, anonymous, lambda, method ref) do the EXACT same thing.

        System.out.println("\n========================================");
        System.out.println("  REAL-WORLD EXAMPLES");
        System.out.println("========================================\n");

        // -------------------------------------------------------
        // 1. BASIC: Supply a default value
        // -------------------------------------------------------
        Supplier<String> defaultName = () -> "Guest User";
        System.out.println("Default: " + defaultName.get());
        // Guest User

        // -------------------------------------------------------
        // 2. FACTORY: Create new objects on demand
        // -------------------------------------------------------
        Supplier<List<String>> emptyCart = () -> new java.util.ArrayList<>();

        List<String> cart1 = emptyCart.get();
        List<String> cart2 = emptyCart.get();
        cart1.add("iPhone");
        cart2.add("Samsung");

        System.out.println("Cart 1: " + cart1);  // [iPhone]
        System.out.println("Cart 2: " + cart2);  // [Samsung]
        System.out.println("Different objects? " + (cart1 != cart2));  // true

        // -------------------------------------------------------
        // 3. REAL-WORLD: Optional.orElseGet() -- LAZY default
        //    This is the #1 use case for Supplier
        // -------------------------------------------------------
        Optional<String> userFromDb = Optional.empty();

        // BAD: orElse() always calls the method, even when value exists
        // String name = userFromDb.orElse(expensiveDatabaseLookup());

        // GOOD: orElseGet() only calls the supplier when value is absent
        String name = userFromDb.orElseGet(() -> fetchDefaultUser());
        System.out.println("\nUser: " + name);
        // fetchDefaultUser() is called only because Optional is empty

        Optional<String> existingUser = Optional.of("Awinas");
        String name2 = existingUser.orElseGet(() -> fetchDefaultUser());
        System.out.println("User: " + name2);
        // fetchDefaultUser() is NOT called because Optional has value

        // -------------------------------------------------------
        // 4. REAL-WORLD: Lazy logging (avoid string concatenation cost)
        // -------------------------------------------------------
        boolean debugEnabled = false;
        String userId = "USR-1001";
        String action = "LOGIN";

        // BAD: String is always built, even if debug is off
        // logger.debug("User " + userId + " performed " + action + " at " + getTimestamp());

        // GOOD: String is built only when debug is actually enabled
        Supplier<String> logMessage = () ->
                "User " + userId + " performed " + action + " at " + getTimestamp();

        logIfEnabled(debugEnabled, logMessage);  // skipped
        logIfEnabled(true, logMessage);           // printed

        // -------------------------------------------------------
        // 5. REAL-WORLD: Random data generators for testing
        // -------------------------------------------------------
        Supplier<String> orderIdGenerator = () ->
                "ORD-" + (new Random().nextInt(9000) + 1000);

        Supplier<String> timestampSupplier = () ->
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("\nGenerated Order: " + orderIdGenerator.get());
        System.out.println("Generated Order: " + orderIdGenerator.get());
        System.out.println("Timestamp: " + timestampSupplier.get());

        // -------------------------------------------------------
        // 6. REAL-WORLD: Configuration with fallback chain
        // -------------------------------------------------------
        String envValue = System.getenv("APP_NAME");  // probably null

        Supplier<String> fromEnv = () -> envValue;
        Supplier<String> fromProperty = () -> System.getProperty("app.name");
        Supplier<String> hardcoded = () -> "MyDefaultApp";

        String appName = Optional.ofNullable(fromEnv.get())
                .orElseGet(() -> Optional.ofNullable(fromProperty.get())
                        .orElseGet(hardcoded));
        System.out.println("\nApp name: " + appName);

        // -------------------------------------------------------
        // 7. Method reference as Supplier
        // -------------------------------------------------------
        Supplier<Double> randomValue = Math::random;
        System.out.println("Random: " + randomValue.get());
        System.out.println("Random: " + randomValue.get());

        // -------------------------------------------------------
        // 8. Passing Supplier as method parameter
        // -------------------------------------------------------
        System.out.println("\n--- Creating 3 orders ---");
        List<String> orderIds = generateMultiple(orderIdGenerator, 3);
        System.out.println("Orders: " + orderIds);
    }

    static String fetchDefaultUser() {
        System.out.println("  [DB] Fetching default user from database...");
        return "default_admin";
    }

    static String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    static void logIfEnabled(boolean enabled, Supplier<String> messageSupplier) {
        if (enabled) {
            System.out.println("[DEBUG] " + messageSupplier.get());
        }
    }

    static <T> List<T> generateMultiple(Supplier<T> supplier, int count) {
        List<T> results = new java.util.ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(supplier.get());
        }
        return results;
    }
}
