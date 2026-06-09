package com.OrderService.service;

import com.OrderService.client.InventoryClient;
import com.OrderService.client.PaymentClient;
import com.OrderService.dtoRequests.*;
import com.OrderService.dtoResponses.PaymentResponse;
import com.OrderService.entity.Order;
import com.OrderService.enums.OrderStatus;
import com.OrderService.events.OrderCreatedEvent;
import com.OrderService.producer.OrderCreatedProducer;
import com.OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderSagaService {

    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;
    private final OrderCreatedProducer orderCreatedProducer;

    public void startOrderSaga(
            Order order,
            CreateOrderRequest request) {

        boolean inventoryReserved = false;

        try {
            reserveInventory(order, request);
            inventoryReserved = true;

            processPayment(order, request);

            confirmOrder(order);

        } catch (Exception ex) {

            compensate(order, inventoryReserved);

            throw new RuntimeException(
                    "Order saga failed: " + ex.getMessage(),
                    ex
            );
        }
    }

    private void reserveInventory(
            Order order,
            CreateOrderRequest request) {

        List<InventoryItemRequest> inventoryItems =
                request.getItems()
                        .stream()
                        .map(item -> {
                            InventoryItemRequest inventoryItem =
                                    new InventoryItemRequest();

                            inventoryItem.setProductId(
                                    item.getProductId());

                            inventoryItem.setQuantity(
                                    item.getQuantity());

                            return inventoryItem;
                        })
                        .toList();

        InventoryRequest inventoryRequest =
                new InventoryRequest();

        inventoryRequest.setOrderId(order.getId());
        inventoryRequest.setItems(inventoryItems);

        inventoryClient.reserveInventory(inventoryRequest);

        order.setStatus(OrderStatus.INVENTORY_RESERVED);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }

    private void processPayment(
            Order order,
            CreateOrderRequest request) {

        PaymentRequest paymentRequest =
                new PaymentRequest();

        paymentRequest.setOrderId(order.getId());
        paymentRequest.setCustomerId(order.getCustomerId());
        paymentRequest.setAmount(order.getTotalAmount());

        PaymentResponse paymentResponse =
                paymentClient.processPayment(paymentRequest);

        if (!paymentResponse.isSuccess()) {
            throw new RuntimeException("Payment failed");
        }

        order.setStatus(OrderStatus.PAYMENT_COMPLETED);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }

    private void confirmOrder(Order order) {

        order.setStatus(OrderStatus.CONFIRMED);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        orderCreatedProducer.sendOrderCreatedEvent(
                new OrderCreatedEvent(
                        order.getId(),
                        "hclakshat@gmail.com",
                        "Order Update",
                        "Order confirmed #" + order.getId()
                )
        );
    }

    private void compensate(
            Order order,
            boolean inventoryReserved) {

        if (inventoryReserved) {
            try {
                inventoryClient.releaseInventory(
                        order.getId());
            } catch (Exception ignored) {
            }
        }

        order.setStatus(OrderStatus.FAILED);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }
}