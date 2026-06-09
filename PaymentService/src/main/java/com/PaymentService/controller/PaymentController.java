package com.PaymentService.controller;

import com.PaymentService.dto.PaymentRequest;
import com.PaymentService.dto.PaymentResponse;
import com.PaymentService.dto.PaymentStatusResponse;
import com.PaymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {

        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentStatusResponse> getPayment(@PathVariable Long paymentId) {

        return ResponseEntity.ok(paymentService.getPayment(paymentId));
    }
}