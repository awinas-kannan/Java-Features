package com.awinas.learning.dp.creational.prototype;

/**
 * Prototype Pattern - Retail Example
 *
 * A ProductListing that can be cloned to quickly create
 * product variants (different sizes, colours) without
 * rebuilding from scratch.
 */
public class ProductListing implements Cloneable {

    private String productId;
    private String name;
    private String brand;
    private String category;
    private double basePrice;
    private String size;
    private String colour;
    private String description;

    public ProductListing(String productId, String name, String brand,
                          String category, double basePrice, String description) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.basePrice = basePrice;
        this.description = description;
    }

    @Override
    public ProductListing clone() {
        try {
            return (ProductListing) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone failed", e);
        }
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "ProductListing{" +
                "id='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", price=₹" + basePrice +
                ", size='" + size + '\'' +
                ", colour='" + colour + '\'' +
                '}';
    }
}
