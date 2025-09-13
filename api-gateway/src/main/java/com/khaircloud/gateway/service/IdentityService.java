package com.khaircloud.gateway.service;

import com.khaircloud.gateway.dto.IntrospectRequest;
import com.khaircloud.gateway.dto.IntrospectResponse;
import com.khaircloud.gateway.interfaces.client.IdentityServiceClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityServiceClient client;

    public Mono<Boolean> introspect(String token) {
        return client.introspect(IntrospectRequest.builder().token(token).build())
                .map(response -> {
                    IntrospectResponse introspectResponse = response.getData();
                    return introspectResponse != null && introspectResponse.isValid();
                }).defaultIfEmpty(false);
    }
}
