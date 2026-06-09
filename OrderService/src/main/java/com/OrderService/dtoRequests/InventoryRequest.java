package com.OrderService.dtoRequests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InventoryRequest {

    private Long orderId;

    private List<InventoryItemRequest> items;

}
