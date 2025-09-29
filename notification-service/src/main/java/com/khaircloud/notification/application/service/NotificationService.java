package com.khaircloud.notification.application.service;

import com.khaircloud.notification.application.dto.request.CreateNotificationRequest;
import com.khaircloud.notification.domain.model.Notification;
import com.khaircloud.notification.infrastructure.repository.NotificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class NotificationService {

    NotificationRepository notificationRepository;

    public void saveNotification(CreateNotificationRequest request) {
        Notification notifyModel = Notification.builder()
                .type(request.getType())
                .content(request.getContent())
                .broadcast(request.isBroadcast())
                .subject(request.getSubject()).build();

        if (request.isBroadcast()) {
            notifyModel.setSenderId(null);
            notifyModel.setReceiverId(null);
        } else {
            notifyModel.setSenderId(request.getSenderId());
            notifyModel.setReceiverId(request.getReceiverId());
        }
        notificationRepository.save(notifyModel);
    }

}
