package com.khaircloud.product.application.service;

import com.khaircloud.product.application.dto.request.CreateProductRequest;
import com.khaircloud.product.application.dto.request.PageableRequest;
import com.khaircloud.product.application.dto.response.ProductResponse;
import com.khaircloud.product.application.mapper.ProductMapper;
import com.khaircloud.product.infrastructure.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;

    public ProductResponse createProduct(CreateProductRequest request) {
        var product = ProductMapper.toProduct(request);
        product = productRepository.save(product);

        return ProductMapper.toProductResponse(product);
    }

    public List<ProductResponse> getInnerProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toProductResponse).toList();
    }

    public Page<ProductResponse> getProducts(PageableRequest request) {
        Sort sorter = request.isDesc() ?
                Sort.by(request.getSortBy()).descending() :
                Sort.by(request.getSortBy()).ascending();
        return productRepository.findAll(PageRequest.of(request.getPage(), request.getSize(), sorter))
                .map(ProductMapper::toProductResponse);
    }

}
