package com.OrderService.service;

import com.OrderService.client.InventoryClient;
import com.OrderService.client.NotificationClient;
import com.OrderService.client.PaymentClient;
import com.OrderService.dtoResponses.*;
import com.OrderService.dtoRequests.*;
import com.OrderService.entity.Order;
import com.OrderService.entity.OrderItem;
import com.OrderService.enums.OrderStatus;
import com.OrderService.events.OrderCreatedEvent;
import com.OrderService.exception.OrderNotFoundException;
import com.OrderService.producer.OrderCreatedProducer;
import com.OrderService.repository.OrderItemRepository;
import com.OrderService.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger logger= LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;

    private final OrderCreatedProducer orderCreatedProducer;
    private final OrderSagaService orderSagaService;


//    @Transactional
//    public OrderResponse createOrder(CreateOrderRequest request) {
//
//        if (request.getItems() == null || request.getItems().isEmpty()) {
//            throw new RuntimeException("Order must contain at least one item");
//        }
//
//        BigDecimal totalAmount = request.getItems()
//                .stream()
//                .map(item -> {
//                    if (item.getUnitPrice() == null) {
//                        throw new RuntimeException("Unit price is missing for product " + item.getProductId());
//                    }
//
//                    if (item.getQuantity() == null || item.getQuantity() <= 0) {
//                        throw new RuntimeException("Invalid quantity for product " + item.getProductId());
//                    }
//
//                    return item.getUnitPrice()
//                            .multiply(BigDecimal.valueOf(item.getQuantity()));
//                })
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        Order order = Order.builder()
//                .customerId(request.getCustomerId())
//                .totalAmount(totalAmount)
//                .status(OrderStatus.CREATED)
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        order = orderRepository.save(order);
//
//        Long orderId = order.getId();
//
//        List<OrderItemResponse> itemResponses = new ArrayList<>();
//
//        for (OrderItemRequest item : request.getItems()) {
//
//            OrderItem orderItem = OrderItem.builder()
//                    .orderId(orderId)
//                    .productId(item.getProductId())
//                    .quantity(item.getQuantity())
//                    .unitPrice(item.getUnitPrice())
//                    .build();
//
//            orderItemRepository.save(orderItem);
//
//            itemResponses.add(
//                    OrderItemResponse.builder()
//                            .productId(item.getProductId())
//                            .quantity(item.getQuantity())
//                            .unitPrice(item.getUnitPrice())
//                            .build()
//            );
//        }
//
//        boolean inventoryReserved = false;
//
//        try {
//            List<InventoryItemRequest> inventoryItems = request.getItems()
//                    .stream()
//                    .map(item -> {
//                        InventoryItemRequest inventoryItem = new InventoryItemRequest();
//                        inventoryItem.setProductId(item.getProductId());
//                        inventoryItem.setQuantity(item.getQuantity());
//                        return inventoryItem;
//                    })
//                    .toList();
//
//            InventoryRequest inventoryRequest = new InventoryRequest();
//            inventoryRequest.setOrderId(orderId);
//            inventoryRequest.setItems(inventoryItems);
//
//            inventoryClient.reserveInventory(inventoryRequest);
//            inventoryReserved = true;
//
//            PaymentRequest paymentRequest = new PaymentRequest();
//            paymentRequest.setOrderId(orderId);
//            paymentRequest.setCustomerId(request.getCustomerId());
//            paymentRequest.setAmount(totalAmount);
//
//            PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);
//
//            if (!paymentResponse.isSuccess()) {
//
//                if (inventoryReserved) {
//                    inventoryClient.releaseInventory(orderId);
//                }
//
//                order.setStatus(OrderStatus.FAILED);
//                order.setUpdatedAt(LocalDateTime.now());
//                orderRepository.save(order);
//
//                sendNotification(order, "Payment failed");
//
//                return buildOrderResponse(order, request, totalAmount, itemResponses);
//            }
//
//            order.setStatus(OrderStatus.CONFIRMED);
//            order.setUpdatedAt(LocalDateTime.now());
//            orderRepository.save(order);
//
////            sendNotification(order, "Order confirmed");    //v1
//
//            orderCreatedProducer.sendOrderCreatedEvent(
//                    new OrderCreatedEvent(
//                            order.getId(),
//                            "hclakshat@gmail.com",
//                            "Order Update",
//                            "Order confirmed #" + order.getId()
//                    )
//            );
//
//            logger.info("Order created successfully with id {}", orderId);
//
//            return buildOrderResponse(order, request, totalAmount, itemResponses);
//
//        } catch (Exception ex) {
//
//            if (inventoryReserved) {
//                try {
//                    inventoryClient.releaseInventory(orderId);
//                } catch (Exception releaseEx) {
//                    logger.error("Failed to release inventory for order {}", orderId, releaseEx);
//                }
//            }
//
//            order.setStatus(OrderStatus.FAILED);
//            order.setUpdatedAt(LocalDateTime.now());
//            orderRepository.save(order);
//
//            sendNotification(order, "Order failed: " + ex.getMessage());
//
//            logger.error("Order creation failed for order {}", orderId, ex);
//
//            return buildOrderResponse(order, request, totalAmount, itemResponses);
//        }
//    }

@Transactional
public OrderResponse createOrder(
        CreateOrderRequest request) {

    if (request.getItems() == null
            || request.getItems().isEmpty()) {

        throw new RuntimeException(
                "Order must contain at least one item");
    }

    BigDecimal totalAmount =
            request.getItems()
                    .stream()
                    .map(item -> {

                        if (item.getUnitPrice() == null) {
                            throw new RuntimeException(
                                    "Unit price is missing for product "
                                            + item.getProductId());
                        }

                        if (item.getQuantity() == null
                                || item.getQuantity() <= 0) {

                            throw new RuntimeException(
                                    "Invalid quantity for product "
                                            + item.getProductId());
                        }

                        return item.getUnitPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

    Order order =
            Order.builder()
                    .customerId(request.getCustomerId())
                    .totalAmount(totalAmount)
                    .status(OrderStatus.CREATED)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

    order = orderRepository.save(order);

    Long orderId = order.getId();

    List<OrderItemResponse> itemResponses =
            new ArrayList<>();

    for (OrderItemRequest item : request.getItems()) {

        OrderItem orderItem =
                OrderItem.builder()
                        .orderId(orderId)
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build();

        orderItemRepository.save(orderItem);

        itemResponses.add(
                OrderItemResponse.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build()
        );
    }

    try {
        orderSagaService.startOrderSaga(
                order,
                request
        );

        logger.info(
                "Order saga completed successfully for order {}",
                orderId
        );

    } catch (Exception ex) {

        logger.error(
                "Order saga failed for order {}",
                orderId,
                ex
        );
    }
    order = orderRepository.findById(orderId)
            .orElse(order);
    return buildOrderResponse(
            order,
            request,
            totalAmount,
            itemResponses
    );
}


    private OrderResponse buildOrderResponse(
            Order order,
            CreateOrderRequest request,
            BigDecimal totalAmount,
            List<OrderItemResponse> itemResponses) {

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(request.getCustomerId())
                .totalAmount(totalAmount)
                .status(order.getStatus().name())
                .items(itemResponses)
                .build();
    }
    private void sendNotification(Order order, String message) {

        try {
            NotificationRequest notification = new NotificationRequest();
            notification.setEmail("hclakshat@gmail.com");
            notification.setSubject("Order Update");
            notification.setMessage(message + " #" + order.getId());

            notificationClient.sendNotification(notification);

        } catch (Exception ex) {
            logger.error("Failed to send notification for order {}", order.getId(), ex);
        }
    }


    public OrderResponse getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        List<OrderItemResponse> items = orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(i -> OrderItemResponse.builder()
                        .productId(i.getProductId())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .build())
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .items(items)
                .build();
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        sendNotification(order, "Order cancelled");
    }
}