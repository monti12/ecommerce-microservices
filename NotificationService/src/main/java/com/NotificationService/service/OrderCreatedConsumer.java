package com.NotificationService.service;

import com.NotificationService.events.OrderCreatedEvent;
import com.NotificationService.dto.NotificationRequest;
import com.NotificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "order-created-topic",
            groupId = "notification-service-group"
    )
    public void consume(OrderCreatedEvent event) {

        NotificationRequest request = new NotificationRequest();

        request.setEmail(event.getEmail());
        request.setSubject(event.getSubject());
        request.setMessage(event.getMessage());

        notificationService.sendNotification(request);

        System.out.println(
                "Kafka V2 notification sent for order "
                        + event.getOrderId()
        );
    }
}