package com.aixtor.ecommerce.inventory.service.repository;

import com.aixtor.ecommerce.inventory.service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductId(String productId);

    Optional<Inventory> findByProductIdAndVariantId(String productId, String variantId);
}