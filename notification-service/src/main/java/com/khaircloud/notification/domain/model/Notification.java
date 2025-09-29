package com.khaircloud.notification.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    @Id
    String id;

    String senderId;

    String receiverId;

    String type;

    String content;

    String subject;

    boolean broadcast = false;

    LocalDateTime createdAt = LocalDateTime.now();

}
