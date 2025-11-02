package com.aixtor.ecommerce.cart.service.model;

public class CartItem {
    private String productId;
    private String variantId;
    private int quantity;


    private String productName;
    private double price;
    public CartItem() {}

    public CartItem(String productId, String variantId, int quantity) {
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getVariantId() { return variantId; }
    public void setVariantId(String variantId) { this.variantId = variantId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    @Override
    public String toString() {
        return "CartItem{" +
                "productId='" + productId + '\'' +
                ", variantId='" + variantId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
