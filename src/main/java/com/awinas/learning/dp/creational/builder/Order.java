package com.awinas.learning.dp.creational.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder Pattern - Retail Example
 *
 * A complex Order object built step-by-step using a fluent builder.
 * Avoids telescoping constructors for objects with many optional fields.
 */
public class Order {

    private final String orderId;
    private final String customerName;
    private final List<String> items;
    private final String shippingAddress;
    private final String paymentMethod;
    private final boolean giftWrap;
    private final String couponCode;
    private final boolean expressDelivery;
    private final String specialInstructions;

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerName = builder.customerName;
        this.items = builder.items;
        this.shippingAddress = builder.shippingAddress;
        this.paymentMethod = builder.paymentMethod;
        this.giftWrap = builder.giftWrap;
        this.couponCode = builder.couponCode;
        this.expressDelivery = builder.expressDelivery;
        this.specialInstructions = builder.specialInstructions;
    }

    @Override
    public String toString() {
        return "Order{" +
                "\n  orderId='" + orderId + '\'' +
                "\n  customer='" + customerName + '\'' +
                "\n  items=" + items +
                "\n  shippingAddress='" + shippingAddress + '\'' +
                "\n  paymentMethod='" + paymentMethod + '\'' +
                "\n  giftWrap=" + giftWrap +
                "\n  couponCode='" + couponCode + '\'' +
                "\n  expressDelivery=" + expressDelivery +
                "\n  specialInstructions='" + specialInstructions + '\'' +
                "\n}";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(String orderId, String customerName) {
        return new Builder(orderId, customerName);
    }

    public static class Builder {
        private String orderId;
        private String customerName;
        private List<String> items = new ArrayList<>();
        private String shippingAddress;
        private String paymentMethod = "COD";
        private boolean giftWrap = false;
        private String couponCode;
        private boolean expressDelivery = false;
        private String specialInstructions;

        private Builder() {
        }

        private Builder(String orderId, String customerName) {
            this.orderId = orderId;
            this.customerName = customerName;
        }

        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder addItem(String item) {
            this.items.add(item);
            return this;
        }

        public Builder shippingAddress(String address) {
            this.shippingAddress = address;
            return this;
        }

        public Builder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder giftWrap(boolean giftWrap) {
            this.giftWrap = giftWrap;
            return this;
        }

        public Builder couponCode(String coupon) {
            this.couponCode = coupon;
            return this;
        }

        public Builder expressDelivery(boolean express) {
            this.expressDelivery = express;
            return this;
        }

        public Builder specialInstructions(String instructions) {
            this.specialInstructions = instructions;
            return this;
        }

        public Order build() {
            if (orderId == null || orderId.isEmpty()) {
                throw new IllegalStateException("Order ID is required!");
            }
            if (customerName == null || customerName.isEmpty()) {
                throw new IllegalStateException("Customer name is required!");
            }
            if (items.isEmpty()) {
                throw new IllegalStateException("Order must have at least one item!");
            }
            if (shippingAddress == null || shippingAddress.isEmpty()) {
                throw new IllegalStateException("Shipping address is required!");
            }
            return new Order(this);
        }
    }
}
