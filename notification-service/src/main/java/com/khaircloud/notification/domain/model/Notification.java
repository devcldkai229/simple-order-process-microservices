package com.khaircloud.notification.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    @Id
    String id;

    String senderId;

    String receiverId;

    String type;

    String body;

    boolean broadcast = false;

    LocalDateTime createdAt = LocalDateTime.now();
}
