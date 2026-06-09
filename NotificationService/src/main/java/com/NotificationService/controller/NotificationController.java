package com.NotificationService.controller;

import com.NotificationService.dto.NotificationRequest;
import com.NotificationService.dto.NotificationResponse;
import com.NotificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponse sendNotification(
            @RequestBody NotificationRequest request) {

        return notificationService.sendNotification(
                request);
    }
}
