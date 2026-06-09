package com.OrderService.repository;


import com.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository
        extends JpaRepository<Order, Long> {
}
