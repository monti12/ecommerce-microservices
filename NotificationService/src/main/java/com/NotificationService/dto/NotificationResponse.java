package com.NotificationService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

    private boolean success;

    private String message;

    public NotificationResponse() {
    }

    public NotificationResponse(
            boolean success,
            String message) {

        this.success = success;
        this.message = message;
    }

}