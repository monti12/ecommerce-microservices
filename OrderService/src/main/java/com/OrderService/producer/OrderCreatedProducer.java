package com.OrderService.producer;

import com.OrderService.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCreatedProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {

        kafkaTemplate.send(
                "order-created-topic",
                String.valueOf(event.getOrderId()),
                event
        );

        System.out.println(
                "Kafka V2 event published for order "
                        + event.getOrderId()
        );
    }
}