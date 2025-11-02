package com.aixtor.ecommerce.inventory.service.controller;


import com.aixtor.ecommerce.inventory.service.DTO.StockUpdateRequest;
import com.aixtor.ecommerce.inventory.service.model.Inventory;
import com.aixtor.ecommerce.inventory.service.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping
    public Inventory create(@RequestBody Inventory inventory) {
        return service.save(inventory);
    }

    @GetMapping("/{productId}")
    public List<Inventory> getByProduct(@PathVariable String productId) {
        return service.getInventoryByProduct(productId);
    }

    @GetMapping("/{productId}/{variantId}")
    public Inventory getByVariant(@PathVariable String productId, @PathVariable String variantId) {
        return service.getInventoryByVariant(productId, variantId);
    }

    @PostMapping("/reserve")
    public Inventory reserveStock(@RequestParam String productId, @RequestParam String variantId, @RequestParam int quantity) {
        return service.reserveStock(productId, variantId, quantity);
    }

    @PostMapping("/release")
    public Inventory releaseStock(@RequestParam String productId, @RequestParam String variantId, @RequestParam int quantity) {
        return service.releaseStock(productId, variantId, quantity);
    }

    @PostMapping("/update")
    public Inventory updateStock(@RequestBody StockUpdateRequest request) {
        return service.updateStock(request);
    }
}
