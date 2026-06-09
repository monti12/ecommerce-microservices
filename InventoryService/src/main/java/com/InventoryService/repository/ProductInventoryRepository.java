package com.InventoryService.repository;

import com.InventoryService.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductInventoryRepository
        extends JpaRepository<ProductInventory, Long> {
}
