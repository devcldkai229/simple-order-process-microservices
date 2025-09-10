package com.khaircloud.product.interfaces.rest;

import com.khaircloud.product.application.dto.ApiResponse;
import com.khaircloud.product.application.dto.request.CreateProductRequest;
import com.khaircloud.product.application.dto.request.PageableRequest;
import com.khaircloud.product.application.dto.response.ProductResponse;
import com.khaircloud.product.application.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse<ProductResponse>> create(@RequestBody CreateProductRequest request) {
        var res = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<ProductResponse>builder()
                .code(200)
                .data(res)
                .build());
    }

    @GetMapping("/inner")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getInnerProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<ProductResponse>>builder()
                .code(200)
                .data(productService.getInnerProducts())
                .build());
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "salesCount") String sortBy,
            @RequestParam(defaultValue = "true") boolean desc
    ) {
        var pageableRequest = PageableRequest.builder()
                .page(page).size(size).sortBy(sortBy).desc(desc).build();

        var res = productService.getProducts(pageableRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<Page<ProductResponse>>builder()
                .code(200)
                .data(res)
                .build());
    }

}
