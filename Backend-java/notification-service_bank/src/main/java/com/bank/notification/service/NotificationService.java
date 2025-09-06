package com.bank.notification.service;

import com.bank.notification.dto.NotificationRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    // Send notification based on type
    public void sendNotification(NotificationRequest request) {
        switch (request.getType().toUpperCase()) {
            case "LOG":
                log.info("Notification Sent => To: {}, Message: {}", request.getTo(), request.getMessage());
                break;
            case "EMAIL":
                sendEmail(request.getTo(), request.getMessage());
                break;
            case "SMS":
                sendSms(request.getTo(), request.getMessage());
                break;
            default:
                log.warn("Unknown notification type: {}", request.getType());
        }
    }

    private void sendEmail(String to, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Bank Notification");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
        log.info("Email sent to {}", to);
    }

    private void sendSms(String to, String message) {
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber("+1234567890"), // Twilio phone number from application.properties
                message
        ).create();
        log.info("SMS sent to {}", to);
    }
}
