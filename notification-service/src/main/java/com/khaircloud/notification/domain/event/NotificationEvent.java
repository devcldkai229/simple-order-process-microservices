package com.khaircloud.notification.domain.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEvent<T> {
    String sender;
    String receiver;
    T body;
    String subject;

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", body=" + body +
                ", subject='" + subject + '\'' +
                '}';
    }
}
