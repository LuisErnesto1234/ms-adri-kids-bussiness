package com.test.product.inventory.infrastructure.adapter.in.api;

import com.test.product.inventory.domain.port.in.product_use_case.CreateProductUseCase;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.ProductRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductRestMapper;
import com.test.product.inventory.infrastructure.utils.ApiResponse;
import com.test.product.inventory.infrastructure.utils.ConstantUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductRestMapper productRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {

        var command = productRestMapper.toCommand(request);

        var product = createProductUseCase.createProduct(command);

        var response = productRestMapper.toResponse(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<ProductResponse>builder()
                .data(response)
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

}
