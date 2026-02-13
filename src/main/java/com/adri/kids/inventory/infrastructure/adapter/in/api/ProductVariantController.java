package com.adri.kids.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.adri.kids.inventory.application.querys.getproductvariants.GetProductVariantsQuery;
import com.adri.kids.inventory.application.querys.getproductvariantsbyid.GetProductVariantByIdQuery;
import com.adri.kids.inventory.application.command.incrementstockproductvariant.IncrementStockProductVariantCommand;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.ProductVariantRequest;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.productvariant.ProductVariantCardResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.productvariant.ProductVariantDetailResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.mapper.ProductVariantRestMapper;
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
@RequestMapping(value = "/api/v1/product-variant")
@RequiredArgsConstructor
public class ProductVariantController {

    private final Pipeline pipeline;
    private final ProductVariantRestMapper productVariantRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<ProductVariantCardResponse>> createProductVariant(
            @Valid @RequestBody ProductVariantRequest request) {
        var command = request.toCommand();

        var entity = command.execute(pipeline);

        var response = productVariantRestMapper.toResponseDetails(entity, entity.product().basePrice());

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
    public ResponseEntity<ApiResponse<PagedResult<ProductVariantCardResponse>>> findAllProductVariants(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String search) {

        var command = new GetProductVariantsQuery(pageable, search);

        var response = command.execute(pipeline);

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }

    @GetMapping(value = "/find-id/{id}")
    public ResponseEntity<ApiResponse<ProductVariantDetailResponse>> findById(@PathVariable UUID id) {
        var query = new GetProductVariantByIdQuery(id);
        var response = query.execute(pipeline);
        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }
}


