package com.PaymentService.dto;

import java.math.BigDecimal;

public record PaymentRequest (Long orderId, Long customerId,
                              BigDecimal amount)
    {

}