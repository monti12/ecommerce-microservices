package com.NotificationService.dto;

import lombok.Data;

@Data
public class NotificationRequest{
    String email;
    String subject;
    String message;


}