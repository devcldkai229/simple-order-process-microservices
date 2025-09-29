package com.khaircloud.notification.listener;

import com.khaircloud.notification.application.dto.request.CreateNotificationRequest;
import com.khaircloud.notification.application.service.NotificationService;
import com.khaircloud.notification.domain.event.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotifyListener {

    NotificationService notificationService;

    @KafkaListener(topics = "verify-account-register-notification")
    public void handleVerifyAccountNotification(NotificationEvent event) {
        log.info(event.toString());
        notificationService.saveNotification(CreateNotificationRequest.builder().type("VERIFY")
                .broadcast(false).content("<button onClick=\"() -> window.location=\"http://localhost:8081/auth/verify\"\">Click here to verify</button>")
                .senderId(event.getSender())
                .receiverId(event.getReceiver())
                .subject(event.getSubject())
                .build());
    }

}
