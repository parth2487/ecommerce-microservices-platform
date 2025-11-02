package com.aixtor.ecommerce.product.service.model;

public class Variant {

    private String variantId;
    private String size;
//    private int stock;

    public Variant() {
    }

    public Variant(String variantId, String size) {
        this.variantId = variantId;
        this.size = size;
//        this.stock = stock;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

//    public int getStock() {
//        return stock;
//    }
//
//    public void setStock(int stock) {
//        this.stock = stock;
//    }

    @Override
    public String toString() {
        return "Variant{" +
                "variantId='" + variantId + '\'' +
                ", size='" + size + '\'' +
//                ", stock=" + stock +
                '}';
    }
}