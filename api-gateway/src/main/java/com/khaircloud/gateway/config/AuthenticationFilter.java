package com.khaircloud.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaircloud.gateway.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Order(-1)
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter {

    ObjectMapper objectMapper;

    public final String[] public_end_points = {
        "/auth/**"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var isPublicEndpoint = Arrays.stream(public_end_points).anyMatch(x -> x
                .matches(exchange.getRequest().getURI().getPath()));

        if (isPublicEndpoint) chain.filter(exchange);
        var headers = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if(headers.isEmpty()) return unauthenticated(exchange.getResponse());

        var token = headers.getFirst().replace("Bearer ", "");


        return null;
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        var bodyUnauthenticated = ApiResponse.builder()
                .code(401)
                .message("Unauthenticated")
                .build();

        try {
            var jsonBody = objectMapper.writeValueAsString(bodyUnauthenticated);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            return response.writeWith(
                    Mono.just(response.bufferFactory().wrap(jsonBody.getBytes()))
            );
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
