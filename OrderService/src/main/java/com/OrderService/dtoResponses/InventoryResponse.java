package com.OrderService.dtoResponses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryResponse {

    private boolean success;

    private String message;

    public InventoryResponse(
            boolean success,
            String message) {

        this.success = success;
        this.message = message;
    }

}