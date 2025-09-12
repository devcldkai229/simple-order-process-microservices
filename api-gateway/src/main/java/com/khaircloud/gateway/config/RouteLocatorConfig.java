package com.khaircloud.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
public class RouteLocatorConfig {

    @Value("${application.prefix-route}")
    private String application_route_api_prefix;

    @Value(("${services.url}"))
    private Map<String, String> routeServices;

    @Autowired
    private DynamicRedisRateLimiter dynamicRedisRateLimiter;

    @Bean
    public RouteLocator routeLocateConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(route -> route
                        .path(application_route_api_prefix + "/identity/**")
                        .filters(routeFilter -> routeFilter
                                .rewritePath(application_route_api_prefix + "/identity/(?<segment>.*)" , "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(configCb -> configCb.setName("identity-circuit-breaker")
                                        .setFallbackUri("forward:/contact-support"))
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                         .setMethods(HttpMethod.GET)
                                         .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                                .requestRateLimiter(rateLimiter -> rateLimiter
                                        .setRateLimiter(new RedisRateLimiter(1,1,1)))
                        )
                        .uri(routeServices.get("identity-service"))
                )
                .route(route -> route.path(application_route_api_prefix + "/product/**")
                        .filters(routeFilter -> routeFilter
                                .rewritePath(application_route_api_prefix + "/product/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(configCb -> configCb.setName("product-circuit-breaker")
                                        .setFallbackUri("forward:/contact-support"))
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                                .requestRateLimiter(rateLimiter -> rateLimiter.setRateLimiter(dynamicRedisRateLimiter)
                                        .setKeyResolver(userKeyResolver()))
                        )
                        .uri(routeServices.get("product-service"))
                ).build();
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(
                exchange.getRequest().getHeaders().getFirst("X-user-plan")
        ).defaultIfEmpty("anonymous");
    }
}
