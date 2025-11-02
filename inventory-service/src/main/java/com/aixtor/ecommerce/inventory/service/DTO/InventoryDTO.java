package com.aixtor.ecommerce.inventory.service.DTO;

public class InventoryDTO {
    private String productId;
    private String variantId;
    private int quantityAvailable;
    private int reservedQuantity;

    public InventoryDTO(String productId, String variantId, int quantityAvailable, int reservedQuantity) {
        this.productId = productId;
        this.variantId = variantId;
        this.quantityAvailable = quantityAvailable;
        this.reservedQuantity = reservedQuantity;
    }

    public InventoryDTO() {
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
}