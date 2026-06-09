package com.OrderService.dtoRequests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {

    private Long orderId;

    private Long customerId;

    private BigDecimal amount;

}