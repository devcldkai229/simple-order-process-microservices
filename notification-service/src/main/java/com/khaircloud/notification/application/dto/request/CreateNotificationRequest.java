package com.khaircloud.notification.application.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNotificationRequest {

    String subject;

    String content;

    String senderId;

    String receiverId;

    String type;

    boolean broadcast;

}
