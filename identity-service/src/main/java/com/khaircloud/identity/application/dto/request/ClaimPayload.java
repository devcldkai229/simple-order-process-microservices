package com.khaircloud.identity.application.dto.request;

import com.khaircloud.identity.domain.model.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimPayload {
    String userId;

    String email;

    String userPlan;

    Set<Role> role;
}