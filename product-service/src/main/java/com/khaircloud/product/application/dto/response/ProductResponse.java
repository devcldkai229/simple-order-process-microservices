package com.khaircloud.product.application.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;

    String name;

    BigDecimal amount;

    Long salesCount = 0L;

    String category;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
