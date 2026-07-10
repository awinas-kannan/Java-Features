package com.awinas.learning.Java.Java08.functionalinterfaces;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * COMPARATOR<T> -- The Sorter / Orderer
 *
 * Signature : int compare(T o1, T o2)
 * Input     : Two values of type T
 * Output    : int (negative = o1 first, 0 = equal, positive = o2 first)
 *
 * WHY IT WAS ENHANCED IN JAVA 8:
 * - Comparator existed since Java 1.2, but writing it was verbose (anonymous classes).
 * - Java 8 made it a functional interface and added powerful static/default methods:
 *   comparing(), thenComparing(), reversed(), naturalOrder(), nullsFirst(), etc.
 * - These methods turn multi-field sorting from 20 lines into 1 line.
 *
 * REAL-WORLD ANALOGY:
 * Think of an e-commerce "Sort By" dropdown:
 * - Price: Low to High  --> Comparator.comparing(Product::getPrice)
 * - Price: High to Low  --> Comparator.comparing(Product::getPrice).reversed()
 * - Name A-Z, then Price --> Comparator.comparing(Product::getName).thenComparing(Product::getPrice)
 */
public class ComparatorExample {

    static class Product {
        String name;
        double price;
        String category;
        Integer rating; // nullable to demonstrate nullsFirst/nullsLast

        Product(String name, double price, String category, Integer rating) {
            this.name = name;
            this.price = price;
            this.category = category;
            this.rating = rating;
        }

        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public Integer getRating() { return rating; }

        @Override
        public String toString() {
            return String.format("%-15s ₹%8.2f  [%s]  ★%s", name, price, category,
                    rating != null ? rating : "N/A");
        }
    }

    // =============================================================
    // STEP 1: UNDERSTAND THE SOURCE -- What does Comparator look like internally?
    //
    //   @FunctionalInterface
    //   public interface Comparator<T> {
    //       int compare(T o1, T o2);      <-- THE one abstract method
    //   }
    //
    //   It takes TWO inputs of type T and returns an int:
    //     negative --> o1 comes first
    //     0        --> equal
    //     positive --> o2 comes first
    //
    //   NOTE: Comparator existed since Java 1.2, but Java 8 made it a
    //   functional interface and added powerful helper methods.
    // =============================================================

    // =============================================================
    // STEP 2: IMPLEMENT using a concrete class (the OLD Java way -- pre Java 8)
    // =============================================================
    static class PriceComparator implements Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return Double.compare(p1.getPrice(), p2.getPrice());
        }
    }

    static class NameComparator implements Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getName().compareTo(p2.getName());
        }
    }

    public static void main(String[] args) {

        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product("iPhone 15", 79999.00, "Phones", 5),
                new Product("Samsung S24", 59999.00, "Phones", 4),
                new Product("MacBook Air", 99999.00, "Laptops", 5),
                new Product("Dell XPS", 89999.00, "Laptops", 4),
                new Product("OnePlus 12", 39999.00, "Phones", null),
                new Product("iPad Pro", 74999.00, "Tablets", 5),
                new Product("Samsung Tab", 29999.00, "Tablets", 3),
                new Product("AirPods Pro", 24999.00, "Audio", null)
        ));

        // =============================================================
        // STEP 3: USE the concrete class -- works but you need a new class
        //         for EVERY different sort order!
        // =============================================================
        Collections.sort(products, new PriceComparator());
        System.out.println("=== Using class (sort by price) ===");
        products.forEach(System.out::println);

        Collections.sort(products, new NameComparator());
        System.out.println("\n=== Using class (sort by name) ===");
        products.forEach(System.out::println);

        // =============================================================
        // STEP 4: ANONYMOUS CLASS -- no separate class, but very verbose
        // =============================================================
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        System.out.println("\n=== Using anonymous class ===");
        products.forEach(System.out::println);

        // =============================================================
        // STEP 5: LAMBDA -- Since Comparator has only ONE abstract method,
        //         the compiler knows this lambda IS the compare() method.
        // =============================================================
        products.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
        System.out.println("\n=== Using lambda ===");
        products.forEach(System.out::println);

        // =============================================================
        // STEP 6: Comparator.comparing() -- Java 8's best shorthand
        //         Even cleaner than a lambda. Extracts the key to compare by.
        // =============================================================
        products.sort(Comparator.comparing(Product::getName));
        System.out.println("\n=== Using Comparator.comparing() ===");
        products.forEach(System.out::println);

        // ALL FIVE (class, anonymous, lambda, comparing, method ref) do the same thing.

        System.out.println("\n========================================");
        System.out.println("  REAL-WORLD EXAMPLES");
        System.out.println("========================================");

        // -------------------------------------------------------
        // 1. REVERSED: Sort by price high to low
        // -------------------------------------------------------
        products.sort(Comparator.comparing(Product::getPrice).reversed());
        System.out.println("\n=== Sort by Price (High to Low) ===");
        products.forEach(System.out::println);

        // -------------------------------------------------------
        // 2. thenComparing: Multi-field sort
        //    Sort by category first, then by price within each category
        // -------------------------------------------------------
        products.sort(
                Comparator.comparing(Product::getCategory)
                        .thenComparing(Product::getPrice)
        );
        System.out.println("\n=== Sort by Category, then Price ===");
        products.forEach(System.out::println);

        // -------------------------------------------------------
        // 3. nullsFirst / nullsLast: Handle null values gracefully
        //    Sort by rating, putting null ratings at the end
        // -------------------------------------------------------
        products.sort(
                Comparator.comparing(
                        Product::getRating,
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );
        System.out.println("\n=== Sort by Rating (nulls last) ===");
        products.forEach(System.out::println);

        // -------------------------------------------------------
        // 4. REAL-WORLD: Complex e-commerce sort
        //    Category A-Z → Rating high-low → Price low-high
        // -------------------------------------------------------
        products.sort(
                Comparator.comparing(Product::getCategory)
                        .thenComparing(Product::getRating,
                                Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(Product::getPrice)
        );
        System.out.println("\n=== Category → Rating (desc) → Price (asc) ===");
        products.forEach(System.out::println);

        // -------------------------------------------------------
        // 5. Comparator with Stream (doesn't modify original list)
        // -------------------------------------------------------
        System.out.println("\n=== Top 3 cheapest products ===");
        products.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(3)
                .forEach(System.out::println);

        // -------------------------------------------------------
        // 6. min / max using Comparator
        // -------------------------------------------------------
        Product cheapest = products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .orElseThrow();
        System.out.println("\nCheapest: " + cheapest);

        Product mostExpensive = products.stream()
                .max(Comparator.comparing(Product::getPrice))
                .orElseThrow();
        System.out.println("Most expensive: " + mostExpensive);

        // -------------------------------------------------------
        // 7. naturalOrder / reverseOrder for simple types
        // -------------------------------------------------------
        List<String> names = Arrays.asList("Kumar", "Awinas", "Zara", "John");

        names.sort(Comparator.naturalOrder());
        System.out.println("\nNatural order: " + names);
        // [Awinas, John, Kumar, Zara]

        names.sort(Comparator.reverseOrder());
        System.out.println("Reverse order: " + names);
        // [Zara, Kumar, John, Awinas]
    }
}
