package com.OrderService.client;

import com.OrderService.dtoRequests.NotificationRequest;
import com.OrderService.dtoResponses.NotificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationFallbackFactory
        implements FallbackFactory<NotificationClient> {

    @Override
    public NotificationClient create(Throwable cause) {

        log.error("Notification service fallback triggered: {}", "Notification service unavailable");

        return new NotificationClient() {

            @Override
            public NotificationResponse sendNotification(
                    NotificationRequest request) {

                log.error("Unable to confirm notification delivery for email {}", request.getEmail());

                NotificationResponse response =
                        new NotificationResponse();

                response.setSuccess(false);
                response.setMessage(
                        "Notification service unavailable");

                return response;
            }
        };
    }
}