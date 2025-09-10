package com.khaircloud.product.application.mapper;

import com.khaircloud.product.application.dto.request.CreateProductRequest;
import com.khaircloud.product.application.dto.response.ProductResponse;
import com.khaircloud.product.domain.model.Product;

public class ProductMapper {

    public static Product toProduct(CreateProductRequest request) {
        return Product.builder().name(request.getName())
                .amount(request.getAmount())
                .salesCount(request.getSalesCount())
                .category(request.getCategory())
                .build();
    }

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .amount(product.getAmount())
                .salesCount(product.getSalesCount())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt() : null)
                .build();
    }

}
