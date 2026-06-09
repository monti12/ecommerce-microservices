package com.InventoryService.exception;


public class InsufficientInventoryException extends RuntimeException {

    public InsufficientInventoryException(Long productId,
                                          Integer requested,
                                          Integer available) {

        super(String.format(
                "Insufficient inventory for product %d. Requested=%d, Available=%d",
                productId,
                requested,
                available));
    }
}
