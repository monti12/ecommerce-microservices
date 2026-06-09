package com.OrderService.dtoResponses;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long orderId;

    private Long customerId;

    private BigDecimal totalAmount;

    private String status;

    private List<OrderItemResponse> items;
}
