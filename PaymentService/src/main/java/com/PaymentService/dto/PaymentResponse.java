package com.PaymentService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentResponse {

    private boolean success;

    private String message;

    private Long paymentId;

    public PaymentResponse() {
    }

    public PaymentResponse(
            boolean success,
            String message,
            Long paymentId) {

        this.success = success;
        this.message = message;
        this.paymentId = paymentId;
    }

}