package com.PaymentService.dto;

import com.PaymentService.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentStatusResponse {

    private Long paymentId;

    private PaymentStatus status;

}