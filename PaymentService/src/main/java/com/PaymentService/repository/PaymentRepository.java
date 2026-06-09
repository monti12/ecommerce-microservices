package com.PaymentService.repository;

import com.PaymentService.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository
        extends JpaRepository<PaymentTransaction, Long> {
}