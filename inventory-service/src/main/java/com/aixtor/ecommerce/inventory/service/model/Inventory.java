package com.aixtor.ecommerce.inventory.service.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "inventory", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "variant_id"})})
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "variant_id", nullable = false)
    private String variantId;

    @Column(name = "quantity_available")
    private int quantityAvailable;

    @Column(name = "reserved_quantity")
    private int reservedQuantity;

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Inventory(String productId, String variantId, int quantityAvailable, int reservedQuantity, Date updatedAt, Integer version) {
        this.productId = productId;
        this.variantId = variantId;
        this.quantityAvailable = quantityAvailable;
        this.reservedQuantity = reservedQuantity;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public Inventory() {
    }

    @Override
    public String toString() {
        return "Inventory{" + "id=" + id + ", productId='" + productId + '\'' + ", variantId='" + variantId + '\'' + ", quantityAvailable=" + quantityAvailable + ", reservedQuantity=" + reservedQuantity + ", updatedAt=" + updatedAt + ", version=" + version + '}';
    }
}