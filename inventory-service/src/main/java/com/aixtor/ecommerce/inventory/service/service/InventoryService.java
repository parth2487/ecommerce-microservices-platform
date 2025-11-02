package com.aixtor.ecommerce.inventory.service.service;


import com.aixtor.ecommerce.inventory.service.DTO.StockUpdateRequest;
import com.aixtor.ecommerce.inventory.service.exception.InventoryNotFoundException;
import com.aixtor.ecommerce.inventory.service.model.Inventory;
import com.aixtor.ecommerce.inventory.service.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public Inventory save(Inventory inventory) {
        inventory.setUpdatedAt(new Date());
        return repository.save(inventory);
    }
    public List<Inventory> getInventoryByProduct(String productId) {
        return repository.findByProductId(productId);
    }

    public Inventory getInventoryByVariant(String productId, String variantId) {
        return repository.findByProductIdAndVariantId(productId, variantId)
                .orElseThrow(() -> new InventoryNotFoundException("Variant not found"));
    }

    @Transactional
    public Inventory reserveStock(String productId, String variantId, int quantity) {
        Inventory inventory = getInventoryByVariant(productId, variantId);
        if (inventory.getQuantityAvailable() < quantity) {
            throw new RuntimeException("Not enough stock");
        }
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() - quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        inventory.setUpdatedAt(new Date());
        return repository.save(inventory);
    }

    @Transactional
    public Inventory releaseStock(String productId, String variantId, int quantity) {
        Inventory inventory = getInventoryByVariant(productId, variantId);
        inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        inventory.setUpdatedAt(new Date());
        return repository.save(inventory);
    }

    @Transactional
    public Inventory updateStock(StockUpdateRequest request) {
        Inventory inventory = repository.findByProductIdAndVariantId(request.getProductId(), request.getVariantId())
                .orElse(new Inventory());
        inventory.setProductId(request.getProductId());
        inventory.setVariantId(request.getVariantId());
        inventory.setQuantityAvailable(request.getQuantityAvailable());
        inventory.setReservedQuantity(request.getReservedQuantity());
        inventory.setUpdatedAt(new Date());
        return repository.save(inventory);
    }
}