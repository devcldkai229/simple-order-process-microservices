package com.khaircloud.gateway.config;

import com.khaircloud.gateway.service.UserPlanService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicRedisRateLimiter extends RedisRateLimiter {

    UserPlanService userPlanService;

    public DynamicRedisRateLimiter(UserPlanService userPlanService) {
        super(1, 1);
        this.userPlanService = userPlanService;
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        UserPlanConfig config = userPlanService.getUserPlan(id);

        Config dynamicConfig = new Config();
        dynamicConfig.setReplenishRate(config.getReplenishRate());
        dynamicConfig.setBurstCapacity(config.getBurstCapacity());
        dynamicConfig.setRequestedTokens(1);
        getConfig().put(routeId, dynamicConfig);

        return super.isAllowed(routeId, id);
    }
}
