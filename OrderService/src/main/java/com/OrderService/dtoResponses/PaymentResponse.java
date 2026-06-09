package com.OrderService.dtoResponses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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