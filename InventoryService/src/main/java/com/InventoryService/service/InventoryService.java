package com.InventoryService.service;

import com.InventoryService.dto.InventoryItemRequest;
import com.InventoryService.dto.InventoryRequest;
import com.InventoryService.dto.InventoryResponse;
import com.InventoryService.dto.ProductStockResponse;
import com.InventoryService.entity.InventoryReservation;
import com.InventoryService.entity.ProductInventory;
import com.InventoryService.repository.InventoryReservationRepository;
import com.InventoryService.repository.ProductInventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private static final Logger logger= LoggerFactory.getLogger(InventoryService.class);

    private final ProductInventoryRepository inventoryRepo;
    private final InventoryReservationRepository reservationRepo;

    @Transactional
    public InventoryResponse reserveInventory(InventoryRequest request) {

        for (InventoryItemRequest item : request.items()) {

            ProductInventory inventory =
                    inventoryRepo.findById(
                                    item.productId())
                            .orElseThrow();

            int freeStock =
                    inventory.getAvailableQuantity()
                            - inventory.getReservedQuantity();

            if (freeStock < item.quantity()) {

                return new InventoryResponse(
                        false,
                        "Insufficient inventory");
            }
        }

        for (InventoryItemRequest item : request.items()) {

            ProductInventory inventory =
                    inventoryRepo.findById(
                                    item.productId())
                            .orElseThrow();

            inventory.setReservedQuantity(
                    inventory.getReservedQuantity()
                            + item.quantity());

            inventoryRepo.save(inventory);

            InventoryReservation reservation =
                    new InventoryReservation();

            reservation.setOrderId(
                    request.orderId());

            reservation.setProductId(
                    item.productId());

            reservation.setQuantity(
                    item.quantity());

            reservationRepo.save(reservation);
        }
        logger.info("inventory reserved");
        return new InventoryResponse(
                true,
                "Inventory reserved");
    }

    @Transactional
    public void releaseInventory(Long orderId) {

        List<InventoryReservation> reservations =
                reservationRepo.findByOrderId(orderId);

        for (InventoryReservation reservation
                : reservations) {

            ProductInventory inventory =
                    inventoryRepo.findById(
                                    reservation.getProductId())
                            .orElseThrow();

            inventory.setReservedQuantity(
                    inventory.getReservedQuantity()
                            - reservation.getQuantity());

            inventoryRepo.save(inventory);
        }

        reservationRepo.deleteAll(reservations);
    }

    public ProductStockResponse getStock(Long productId) {

        ProductInventory inventory =
                inventoryRepo.findById(productId)
                        .orElseThrow();

        ProductStockResponse response =
                new ProductStockResponse();

        response.setProductId(productId);

        response.setAvailableQuantity(
                inventory.getAvailableQuantity());

        response.setReservedQuantity(
                inventory.getReservedQuantity());

        return response;
    }
}
