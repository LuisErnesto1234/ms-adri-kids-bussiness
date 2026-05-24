package com.adri.kids.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.adri.kids.inventory.application.querys.product.getproduct.GetProductsQuery;
import com.adri.kids.inventory.application.querys.product.getproductbyid.GetProductByIdQuery;
import com.adri.kids.inventory.application.querys.product.getproductsbycategoryid.GetProductsByCategoryIdQuery;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.product.CreateProductRequest;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.product.ProductCardResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.product.ProductDetailResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.mapper.ProductRestMapper;
import com.adri.kids.shared.domain.dtos.ApiResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final Pipeline pipeline;
    private final ProductRestMapper productRestMapper;

    @PostMapping(path = "/create")
    public ResponseEntity<ApiResponse<ProductCardResponse>> createProduct(@Valid @RequestBody CreateProductRequest request) {
        var command = request.toCommand();
        var domainResponse = command.execute(pipeline);
        var response = productRestMapper.toResponseCard(domainResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildCreated(response));
    }

    @GetMapping(path = "/find-all")
    public ResponseEntity<ApiResponse<PagedResult<ProductCardResponse>>> findAllProducts(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String search) {

        var query = new GetProductsQuery(pageable, search);

        var responseDomain = query.execute(pipeline);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.buildOk(responseDomain));

    }

    @GetMapping(path = "/find-id/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> findProductById(@PathVariable UUID id) {
        var query = new GetProductByIdQuery(id);
        var responseDomain = query.execute(pipeline);
        return ResponseEntity.ok(ApiResponse.buildOk(responseDomain));
    }

    @GetMapping(path = "/find-all/{categoryId}/category")
    public ResponseEntity<ApiResponse<PagedResult<ProductCardResponse>>> getAllProductsByCategory(@PathVariable UUID categoryId,
                                                                                                  @PageableDefault Pageable pageable) {
        var query = new GetProductsByCategoryIdQuery(categoryId, pageable.getPageNumber(), pageable.getPageSize());
        var resultPage = query.execute(pipeline);
        var productContentResponse = resultPage.getContent()
                .stream()
                .map(productRestMapper::toResponseCard)
                .toList();
        var pageProductResponse = new PagedResult<>(
                productContentResponse,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.buildOk(pageProductResponse));
    }
}
