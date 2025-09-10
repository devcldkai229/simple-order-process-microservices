package com.khaircloud.identity.infrastructure.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.net.URI;

public class VerifyEmailLambdaHandler implements RequestHandler<SNSEvent, String>{

    private final SesClient sesClient = SesClient.builder()
            .endpointOverride(URI.create("http://localhost:4566"))
            .region(Region.US_EAST_1)
            .build();

    @Override
    public String handleRequest(SNSEvent snsEvent, Context context) {
        for (SNSEvent.SNSRecord snsRecord : snsEvent.getRecords()) {
            var receiver = snsRecord.getSNS().getMessage();
            var token = snsRecord.getSNS().getMessageAttributes().get("token").getValue();

            SendEmailRequest request = SendEmailRequest.builder()
                    .source("noreply@test.com")
                    .destination(Destination.builder().toAddresses(receiver).build())
                    .message(Message.builder()
                            .subject(Content.builder().data("Verify your account").build())
                            .body(Body.builder()
                                    .text(Content.builder()
                                            .data("Click to verify: http://localhost:8080/identity/auth/verify?token=" + token)
                                            .build())
                                    .build())
                            .build())
                    .build();

            sesClient.sendEmail(request);
            context.getLogger().log("✅ Email sent to " + receiver);
        }

        return "Done";
    }
}
