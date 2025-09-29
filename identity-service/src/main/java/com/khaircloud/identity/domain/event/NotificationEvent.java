package com.khaircloud.identity.domain.event;

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
}
