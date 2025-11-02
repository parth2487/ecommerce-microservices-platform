package com.aixtor.ecommerce.inventory.service.exception;


public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String message) {
        super(message);
    }
}