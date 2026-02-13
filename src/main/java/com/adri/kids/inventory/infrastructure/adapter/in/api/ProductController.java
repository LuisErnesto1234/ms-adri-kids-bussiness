package com.adri.kids.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.adri.kids.inventory.application.querys.getproduct.GetProductsQuery;
import com.adri.kids.inventory.application.querys.getproductbyid.GetProductByIdQuery;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.CreateProductRequest;
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
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final Pipeline pipeline;
    private final ProductRestMapper productRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<ProductCardResponse>> createProduct(@Valid @RequestBody CreateProductRequest request) {
        var command = request.toCommand();
        var domainResponse = command.execute(pipeline);
        var response = productRestMapper.toResponseCard(domainResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildCreated(response));
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<ApiResponse<PagedResult<ProductCardResponse>>> findAllProducts(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String search) {

        var query = new GetProductsQuery(pageable, search);

        var responseDomain = query.execute(pipeline);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.buildOk(responseDomain));

    }

    @GetMapping(value = "/find-id/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> findProductById(@PathVariable UUID id) {
        var query = new GetProductByIdQuery(id);
        var responseDomain = query.execute(pipeline);
        return ResponseEntity.ok(ApiResponse.buildOk(responseDomain));
    }
}
