package com.awinas.learning.dp.creational.prototype;

/**
 * Demo: A seller creates a base product listing and clones it
 * to quickly create variants (size/colour) instead of filling
 * all details from scratch each time.
 */
public class PrototypeDemo {

    public static void main(String[] args) {
        // Seller creates the base product listing (expensive setup)
        ProductListing baseTShirt = new ProductListing(
                "SKU-5001",
                "Men's Round Neck T-Shirt",
                "Levi's",
                "Clothing > Men > T-Shirts",
                799.00,
                "100% cotton, regular fit, machine washable"
        );
        baseTShirt.setSize("M");
        baseTShirt.setColour("Black");

        System.out.println("=== Base Listing ===");
        System.out.println(baseTShirt);

        // Clone to create size variants
        System.out.println("\n=== Cloned Variants (different sizes) ===");

        ProductListing smallVariant = baseTShirt.clone();
        smallVariant.setProductId("SKU-5002");
        smallVariant.setSize("S");
        System.out.println(smallVariant);

        ProductListing largeVariant = baseTShirt.clone();
        largeVariant.setProductId("SKU-5003");
        largeVariant.setSize("L");
        System.out.println(largeVariant);

        ProductListing xlVariant = baseTShirt.clone();
        xlVariant.setProductId("SKU-5004");
        xlVariant.setSize("XL");
        xlVariant.setBasePrice(899.00); // XL costs a bit more
        System.out.println(xlVariant);

        // Clone to create colour variants
        System.out.println("\n=== Cloned Variants (different colours) ===");

        ProductListing whiteVariant = baseTShirt.clone();
        whiteVariant.setProductId("SKU-5005");
        whiteVariant.setColour("White");
        System.out.println(whiteVariant);

        ProductListing navyVariant = baseTShirt.clone();
        navyVariant.setProductId("SKU-5006");
        navyVariant.setColour("Navy Blue");
        System.out.println(navyVariant);

        // Verify originals are untouched
        System.out.println("\n=== Original (unchanged) ===");
        System.out.println(baseTShirt);
    }
}
