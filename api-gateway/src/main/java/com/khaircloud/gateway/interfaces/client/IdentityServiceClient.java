package com.khaircloud.gateway.interfaces.client;

import com.khaircloud.gateway.dto.ApiResponse;
import com.khaircloud.gateway.dto.IntrospectRequest;
import com.khaircloud.gateway.dto.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@HttpExchange(url = "/auth")
public interface IdentityServiceClient {
    @PostExchange(url = "/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);

}
