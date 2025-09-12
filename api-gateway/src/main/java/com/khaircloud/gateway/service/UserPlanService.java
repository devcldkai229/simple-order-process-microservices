package com.khaircloud.gateway.service;

import com.khaircloud.gateway.config.UserPlanConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserPlanService {

    private final Map<String, UserPlanConfig> planConfigs = Map.of(
            "FREE", new UserPlanConfig(5, 10),
            "PREMIUM", new UserPlanConfig(20, 50),
            "VIP", new UserPlanConfig(100,200)
    );

    public UserPlanConfig getUserPlan(String plan) {
        return planConfigs.get(plan.toUpperCase());
    }

}
