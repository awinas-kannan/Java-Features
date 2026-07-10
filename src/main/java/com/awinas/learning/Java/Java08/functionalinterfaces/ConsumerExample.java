package com.awinas.learning.Java.Java08.functionalinterfaces;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * CONSUMER<T> -- The Performer / Side-Effect Handler
 *
 * Signature : void accept(T t)
 * Input     : One value of type T
 * Output    : Nothing (void)
 *
 * WHY IT EXISTS:
 * - Sometimes you just want to DO something with a value -- log it, save it,
 *   send it, print it -- without returning anything.
 * - Consumer lets you pass "action" as a parameter.
 * - It powers Stream.forEach(), Stream.peek(), Iterable.forEach(), etc.
 *
 * REAL-WORLD ANALOGY:
 * Think of a notification service -- you give it a message (input),
 * it sends the notification (side-effect), and returns nothing.
 *
 * DEFAULT METHOD:
 * - andThen(Consumer) --> performs this action first, then the other
 *   (Note: there is no compose() unlike Function, because void can't be chained backward)
 */
public class ConsumerExample {

    // =============================================================
    // STEP 1: UNDERSTAND THE SOURCE -- What does Consumer look like internally?
    //
    //   @FunctionalInterface
    //   public interface Consumer<T> {
    //       void accept(T t);      <-- THE one abstract method
    //   }
    //
    //   It takes ONE input and returns NOTHING (void).
    //   Any logic that "does something" without giving back a result is a Consumer.
    //   Examples: printing, logging, saving to DB, sending email.
    // =============================================================

    // =============================================================
    // STEP 2: Create your OWN functional interface (to understand the concept)
    //
    //   @FunctionalInterface
    //   interface MyConsumer<T> {
    //       void accept(T t);
    //   }
    //
    //   This is exactly what java.util.function.Consumer is.
    //   (Your existing ConsumerImpl.java is actually doing this!)
    // =============================================================

    // =============================================================
    // STEP 3: IMPLEMENT using a concrete class (the OLD Java way)
    //         This is what your ConsumerImpl.java already does.
    // =============================================================
    static class PrintConsumer implements Consumer<String> {
        @Override
        public void accept(String value) {
            System.out.println("[PRINT] " + value);
        }
    }

    static class LogConsumer implements Consumer<String> {
        @Override
        public void accept(String value) {
            System.out.println("[LOG] " + java.time.LocalDateTime.now() + " : " + value);
        }
    }

    public static void main(String[] args) {

        // =============================================================
        // STEP 4: USE the concrete class -- works but too much boilerplate
        // =============================================================
        Consumer<String> printer1 = new PrintConsumer();
        printer1.accept("Hello from class implementation!");

        Consumer<String> logger1 = new LogConsumer();
        logger1.accept("User logged in");

        // =============================================================
        // STEP 5: ANONYMOUS CLASS -- no separate class needed, but verbose
        // =============================================================
        Consumer<String> printer2 = new Consumer<String>() {
            @Override
            public void accept(String value) {
                System.out.println("[PRINT] " + value);
            }
        };
        printer2.accept("Hello from anonymous class!");

        // =============================================================
        // STEP 6: LAMBDA -- Since Consumer has only ONE abstract method (accept),
        //         the compiler knows the lambda IS the accept() method.
        //         Note: no return statement needed because accept() returns void.
        // =============================================================
        Consumer<String> printer3 = value -> System.out.println("[PRINT] " + value);
        printer3.accept("Hello from lambda!");

        // =============================================================
        // STEP 7: METHOD REFERENCE -- Even shorter
        //         System.out::println is shorthand for (s) -> System.out.println(s)
        // =============================================================
        Consumer<String> printer4 = System.out::println;
        printer4.accept("Hello from method reference!");

        // ALL FOUR (class, anonymous, lambda, method ref) do the EXACT same thing.

        System.out.println("\n========================================");
        System.out.println("  REAL-WORLD EXAMPLES");
        System.out.println("========================================\n");

        // -------------------------------------------------------
        // 1. BASIC: Print a value
        // -------------------------------------------------------
        Consumer<String> print = System.out::println;
        print.accept("Hello from Consumer!");

        // -------------------------------------------------------
        // 2. REAL-WORLD: Process an order
        // -------------------------------------------------------
        Consumer<Map<String, String>> logOrder = order ->
                System.out.println("[LOG] Order received: " + order.get("id"));

        Consumer<Map<String, String>> validateOrder = order -> {
            String amount = order.get("amount");
            if (Double.parseDouble(amount) <= 0) {
                throw new RuntimeException("Invalid order amount!");
            }
            System.out.println("[VALIDATE] Order " + order.get("id") + " is valid");
        };

        Consumer<Map<String, String>> sendConfirmation = order ->
                System.out.println("[EMAIL] Confirmation sent for order " + order.get("id"));

        // Chain all three actions using andThen
        Consumer<Map<String, String>> processOrder = logOrder
                .andThen(validateOrder)
                .andThen(sendConfirmation);

        Map<String, String> order = new HashMap<>();
        order.put("id", "ORD-1001");
        order.put("amount", "1500.00");
        order.put("customer", "Awinas");

        System.out.println("--- Processing Order ---");
        processOrder.accept(order);
        // [LOG] Order received: ORD-1001
        // [VALIDATE] Order ORD-1001 is valid
        // [EMAIL] Confirmation sent for order ORD-1001

        // -------------------------------------------------------
        // 3. forEach: The most common use of Consumer
        // -------------------------------------------------------
        List<String> items = Arrays.asList("Laptop", "Phone", "Tablet", "Watch");

        System.out.println("\n--- Shopping Cart ---");
        items.forEach(item -> System.out.println("  🛒 " + item));

        // -------------------------------------------------------
        // 4. REAL-WORLD: Notification system -- different channels
        // -------------------------------------------------------
        Consumer<String> emailNotify = msg ->
                System.out.println("[EMAIL] " + msg);

        Consumer<String> smsNotify = msg ->
                System.out.println("[SMS] " + msg);

        Consumer<String> slackNotify = msg ->
                System.out.println("[SLACK] " + msg);

        Consumer<String> notifyAll = emailNotify
                .andThen(smsNotify)
                .andThen(slackNotify);

        System.out.println("\n--- Sending Alert ---");
        notifyAll.accept("Server CPU is at 95%!");
        // [EMAIL] Server CPU is at 95%!
        // [SMS] Server CPU is at 95%!
        // [SLACK] Server CPU is at 95%!

        // -------------------------------------------------------
        // 5. Map.forEach uses BiConsumer (Consumer's two-arg cousin)
        // -------------------------------------------------------
        Map<String, Integer> prices = new HashMap<>();
        prices.put("iPhone", 79999);
        prices.put("Samsung", 59999);
        prices.put("OnePlus", 39999);

        System.out.println("\n--- Price List ---");
        prices.forEach((product, price) ->
                System.out.println("  " + product + " : ₹" + price));

        // -------------------------------------------------------
        // 6. Passing Consumer as method parameter
        //    Same data, different actions
        // -------------------------------------------------------
        List<String> users = Arrays.asList("Awinas", "Kumar", "John");

        System.out.println("\n--- Greet users ---");
        performAction(users, name -> System.out.println("  Welcome, " + name + "!"));

        System.out.println("--- Log users ---");
        performAction(users, name -> System.out.println("  [LOG] User login: " + name));
    }

    static <T> void performAction(List<T> list, Consumer<T> action) {
        list.forEach(action);
    }
}
