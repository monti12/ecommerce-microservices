package com.OrderService.client;

import com.OrderService.dtoRequests.NotificationRequest;
import com.OrderService.dtoResponses.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notification-service", fallbackFactory = NotificationFallbackFactory.class)
public interface NotificationClient {

    @PostMapping("/notifications")
    NotificationResponse sendNotification(@RequestBody NotificationRequest request);
}