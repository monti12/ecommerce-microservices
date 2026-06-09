package com.InventoryService.controller;


import com.InventoryService.dto.InventoryRequest;
import com.InventoryService.dto.InventoryResponse;
import com.InventoryService.dto.ProductStockResponse;
import com.InventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/reserve")
    public ResponseEntity<InventoryResponse> reserveInventory(@RequestBody InventoryRequest request) {

        log.info("Inventory reserve API hit for order {}", request.orderId());

        return ResponseEntity.ok(inventoryService.reserveInventory(request));
    }

    @PostMapping("/release/{orderId}")
    public ResponseEntity<Void> releaseInventory(@PathVariable("orderId") Long orderId) {

        log.info("Inventory release API hit for order {}", orderId);

        inventoryService.releaseInventory(orderId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductStockResponse> getStock(@PathVariable("productId") Long productId) {

        log.info("Inventory stock API hit for product {}", productId);

        return ResponseEntity.ok(inventoryService.getStock(productId));
    }
}