package com.test.product.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.test.product.inventory.application.querys.getproductvariants.GetProductVariantsQuery;
import com.test.product.inventory.application.usecases.incrementstockproductvariant.IncrementStockProductVariantCommand;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.ProductVariantRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductVariantSummariesResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductVariantRestMapper;
import com.test.product.shared.domain.ApiResponse;
import com.test.product.shared.domain.PagedResult;

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
@RequestMapping(value = "/api/v1/product-variant")
@RequiredArgsConstructor
public class ProductVariantController {

    private final Pipeline pipeline;
    private final ProductVariantRestMapper productVariantRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<ProductVariantSummariesResponse>> createProductVariant(
            @Valid @RequestBody ProductVariantRequest request) {
        var command = productVariantRestMapper.toCommand(request);

        var entity = command.execute(pipeline);

        var response = productVariantRestMapper.toResponseDetails(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildCreated(response));
    }

    @PatchMapping(value = "/{id}/increment-stock/{quantity}")
    public ResponseEntity<ApiResponse<Void>> incrementStockProductVariant(@PathVariable UUID id,
                                                                          @PathVariable Integer quantity) {
        var command = new IncrementStockProductVariantCommand(id, quantity);

        command.execute(pipeline);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.buildOk());
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<ApiResponse<PagedResult<ProductVariantSummariesResponse>>> findAllProductVariants(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String search) {

        var command = new GetProductVariantsQuery(pageable, search);

        var response = command.execute(pipeline);

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }
}


