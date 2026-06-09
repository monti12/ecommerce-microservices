package com.PaymentService.exception;

public class PaymentNotFoundException
        extends RuntimeException {

    public PaymentNotFoundException(Long paymentId) {
        super("Payment not found: " + paymentId);
    }
}