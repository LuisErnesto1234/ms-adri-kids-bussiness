package com.test.product.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.test.product.inventory.application.querys.getproduct.GetProductsQuery;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.CreateProductRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductSummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductRestMapper;
import com.test.product.shared.domain.ApiResponse;
import com.test.product.shared.domain.PagedResult;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final Pipeline pipeline;
    private final ProductRestMapper productRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<ProductSummaryResponse>> createProduct(@Valid @RequestBody CreateProductRequest request) {

        var command = request.toCommand();

        var domainResponse = command.execute(pipeline);

        var response = productRestMapper.toResponse(domainResponse);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildCreated(response));
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<ApiResponse<PagedResult<ProductSummaryResponse>>> findAllProducts(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String search) {

        var query = new GetProductsQuery(pageable, search);

        var responseDomain = query.execute(pipeline);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.buildOk(responseDomain));

    }
}
