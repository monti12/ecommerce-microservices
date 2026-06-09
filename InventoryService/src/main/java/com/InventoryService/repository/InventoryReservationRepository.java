package com.InventoryService.repository;

import com.InventoryService.entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, Long> {

    List<InventoryReservation> findByOrderId(Long orderId);
}
