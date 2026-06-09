package com.OrderService.client;

import com.OrderService.dtoRequests.InventoryRequest;
import com.OrderService.dtoResponses.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "inventory-service", fallbackFactory = InventoryFallbackFactory.class)
public interface InventoryClient {

    @PostMapping("/inventory/reserve")
    InventoryResponse reserveInventory(@RequestBody InventoryRequest request);

    @PostMapping("/inventory/release/{orderId}")
    void releaseInventory(@PathVariable Long orderId);
}