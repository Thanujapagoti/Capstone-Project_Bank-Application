package com.bank.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String to;           // Email or phone
    private String message;
    private String type;         // LOG / EMAIL / SMS
}
