package com.PaymentService.service;

import com.PaymentService.dto.PaymentRequest;
import com.PaymentService.dto.PaymentResponse;
import com.PaymentService.dto.PaymentStatusResponse;
import com.PaymentService.entity.PaymentStatus;
import com.PaymentService.entity.PaymentTransaction;
import com.PaymentService.exception.PaymentNotFoundException;
import com.PaymentService.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger logger= LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;

    public PaymentResponse processPayment(PaymentRequest request) {

        PaymentTransaction payment = new PaymentTransaction();

        payment.setOrderId(request.orderId());
        payment.setCustomerId(request.customerId());
        payment.setAmount(request.amount());

        if (request.amount().doubleValue() > 10000) {

            payment.setStatus(PaymentStatus.FAILED);

            payment = paymentRepository.save(payment);

            return new PaymentResponse(
                    false,
                    "Payment failed",
                    payment.getId()
            );
        }

        payment.setStatus(PaymentStatus.SUCCESS);

        payment = paymentRepository.save(payment);
        logger.info("Payment processed");
        return new PaymentResponse(
                true,
                "Payment successful",
                payment.getId()
        );
    }

    public PaymentStatusResponse getPayment(
            Long paymentId) {

        PaymentTransaction payment = paymentRepository.findById(paymentId)
                        .orElseThrow(
                                () -> new PaymentNotFoundException(
                                        paymentId));

        PaymentStatusResponse response =
                new PaymentStatusResponse();

        response.setPaymentId(paymentId);
        response.setStatus(payment.getStatus());

        return response;
    }
}