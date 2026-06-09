package com.OrderService.client;


import com.OrderService.dtoRequests.InventoryRequest;
import com.OrderService.dtoResponses.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryFallbackFactory
        implements FallbackFactory<InventoryClient> {

    Logger logger= LoggerFactory.getLogger(InventoryFallbackFactory.class);

    @Override
    public InventoryClient create(Throwable cause) {

        return new InventoryClient() {

            @Override
            public InventoryResponse reserveInventory(
                    InventoryRequest request) {

                logger.error(
                        "Inventory service fallback triggered",
                        "Inventory service unavailable");

                throw new RuntimeException(
                        "Inventory service unavailable");
            }

            @Override
            public void releaseInventory(
                    Long orderId) {

                logger.error(
                        "Release inventory failed",
                        "Failed to release inventory");
            }
        };
    }
}