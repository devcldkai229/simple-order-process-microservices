package com.khaircloud.gateway;

import com.khaircloud.gateway.config.DynamicRedisRateLimiter;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableWebFlux
public class GatewayApplication {

    @Value("${application.prefix-route}")
    private String application_route_api_prefix;

    @Value("${services.url.identity-service}")
    private String identity_service_url;

    @Value("${services.url.product-service}")
    private String product_service_url;

    @Autowired
    DynamicRedisRateLimiter dynamicRedisRateLimiter;

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
                        .uri(identity_service_url)
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
                        .uri(product_service_url)
                ).build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(10))
                        .build()).build());
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(
                exchange.getRequest().getHeaders().getFirst("X-user-plan")
        ).defaultIfEmpty("FREE");
    }

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
