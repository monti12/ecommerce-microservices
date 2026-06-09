package com.NotificationService.service;

import com.NotificationService.dto.NotificationRequest;
import com.NotificationService.dto.NotificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    private static final Logger logger= LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    EmailService emailService;
    public NotificationResponse sendNotification(
            NotificationRequest request) {

        emailService.sendEmail(
                request.getEmail(),
                request.getSubject(),
                request.getMessage()
        );
        log.info("Sending notification");

        log.info("To      : {}", request.getEmail());
        log.info("Subject : {}", request.getSubject());
        log.info("Message : {}", request.getMessage());
        logger.info("NotificationSent");
        return new NotificationResponse(
                true,
                "Notification sent"
        );
    }
}