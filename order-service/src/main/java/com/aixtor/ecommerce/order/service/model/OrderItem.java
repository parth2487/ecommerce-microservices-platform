package com.aixtor.ecommerce.order.service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String variantId;
    private Integer quantity;
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" + "id=" + id + ", productId='" + productId + '\'' + ", variantId='" + variantId + '\'' + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", order=" + order + '}';
    }
}
