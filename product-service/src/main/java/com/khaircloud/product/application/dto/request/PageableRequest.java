package com.khaircloud.product.application.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageableRequest {

    int page;
    int size;
    String sortBy;
    boolean desc;


}
