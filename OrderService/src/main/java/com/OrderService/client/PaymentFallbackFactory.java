package com.OrderService.client;

import com.OrderService.dtoRequests.PaymentRequest;
import com.OrderService.dtoResponses.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentFallbackFactory
        implements FallbackFactory<PaymentClient> {

    @Override
    public PaymentClient create(Throwable cause) {

        log.error(
                "Payment service fallback triggered",
                "Payment service unavailable");

        return new PaymentClient() {

            @Override
            public PaymentResponse processPayment(
                    PaymentRequest request) {

                log.error(
                        "Unable to process payment for order {}",
                        request.getOrderId());

                return PaymentResponse.builder()
                        .success(false)
                        .message("Payment service unavailable")
                        .build();
            }
        };
    }
}