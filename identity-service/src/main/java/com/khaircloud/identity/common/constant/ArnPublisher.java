package com.khaircloud.identity.common.constant;

import lombok.Getter;

@Getter
public enum ArnPublisher {
    
    EVENT_VERIFICATION_EMAIL("arn:aws:sns:us-east-1:000000000000:email-verification", "VERIFY_EMAIL");

    ArnPublisher(String arn, String type) {
        this.arn = arn;
        this.type = type;
    }

    private final String arn;
    private final String type;
}
