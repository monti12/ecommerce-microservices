package com.InventoryService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "product_inventory")
@Getter
@Setter
public class ProductInventory {

    @Id
    private Long productId;

    private Integer availableQuantity;

    private Integer reservedQuantity;

    public Integer getFreeStock() {
        return availableQuantity - reservedQuantity;
    }

}