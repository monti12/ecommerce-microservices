package com.InventoryService.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryResponse {

    private boolean success;

    private String message;

    public InventoryResponse() {
    }

    public InventoryResponse(
            boolean success,
            String message) {

        this.success = success;
        this.message = message;
    }

}
