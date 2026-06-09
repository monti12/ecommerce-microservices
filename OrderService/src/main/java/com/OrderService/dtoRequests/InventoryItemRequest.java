package com.OrderService.dtoRequests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItemRequest {

    private Long productId;
    private Integer quantity;

}