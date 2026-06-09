package com.InventoryService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockResponse{

    Long productId;
    Integer availableQuantity;
     Integer reservedQuantity;



}