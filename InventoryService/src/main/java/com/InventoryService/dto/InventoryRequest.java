package com.InventoryService.dto;

import java.util.List;

public record InventoryRequest(Long orderId,
                              List<InventoryItemRequest> items)
{

}
