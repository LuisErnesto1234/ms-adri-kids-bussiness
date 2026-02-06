package com.test.product.inventory.infrastructure.adapter.in.api;

import com.test.product.inventory.application.usecases.incrementstockproductvariant.IncrementStockProductVariantCommand;
import com.test.product.inventory.domain.port.in.product_variant_use_case.CreateProductVariantUseCase;
import com.test.product.inventory.domain.port.in.product_variant_use_case.IncrementProductVariantStockUseCase;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.ProductVariantRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductVariantResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductVariantRestMapper;
import com.test.product.inventory.infrastructure.utils.ApiResponse;
import com.test.product.inventory.infrastructure.utils.ConstantUtil;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/product-variant")
@RequiredArgsConstructor
public class ProductVariantController {

    private final CreateProductVariantUseCase createProductVariantUseCase;
    private final IncrementProductVariantStockUseCase incrementProductVariantStockUseCase;
    private final ProductVariantRestMapper productVariantRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<ProductVariantResponse>> createProductVariant(@Valid @RequestBody ProductVariantRequest request) {
        var command = productVariantRestMapper.toCommand(request);

        var entity = createProductVariantUseCase.createProductVariant(command);

        var response = productVariantRestMapper.toResponse(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<ProductVariantResponse>builder()
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .data(response)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

    @PatchMapping(value = "/{id}/increment-stock/{quantity}")
    public ResponseEntity<ApiResponse<Void>> incrementStockProductVariant(@PathVariable UUID id,
                                                                                            @PathVariable Integer quantity) {
        var command = new IncrementStockProductVariantCommand(id, quantity);
        
        incrementProductVariantStockUseCase.incrementProductVariantStock(command);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<Void>builder()
                .code(ConstantUtil.OK_CODE_HTTP)
                .message(ConstantUtil.UPDATE_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }
}
