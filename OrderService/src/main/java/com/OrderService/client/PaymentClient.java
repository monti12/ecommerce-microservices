package com.OrderService.client;

import com.OrderService.dtoRequests.PaymentRequest;
import com.OrderService.dtoResponses.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", fallbackFactory = PaymentFallbackFactory.class)
public interface PaymentClient {
    @PostMapping("/payments")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);
}