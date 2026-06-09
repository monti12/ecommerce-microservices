package com.OrderService.dtoRequests;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    private String email;

    private String subject;

    private String message;

}