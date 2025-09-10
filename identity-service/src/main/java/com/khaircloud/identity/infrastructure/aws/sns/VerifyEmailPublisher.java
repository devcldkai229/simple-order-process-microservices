package com.khaircloud.identity.infrastructure.aws.sns;

import com.khaircloud.identity.common.constant.ArnPublisher;
import com.khaircloud.identity.domain.event.NotificationEvent;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerifyEmailPublisher {

    SnsTemplate snsTemplate;

    public void sendVerificationEmail(NotificationEvent event, String token) {
        snsTemplate.convertAndSend(
                ArnPublisher.EVENT_VERIFICATION_EMAIL.toString(),
                event.getReceiver(),
                Map.of("token", token)
        );
    }

}
