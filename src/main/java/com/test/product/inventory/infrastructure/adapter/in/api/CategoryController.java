package com.test.product.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.test.product.inventory.application.usecases.disablecategory.DisableCategoryCommand;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.CreateCategoryRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.CategorySummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.CategoryRestMapper;
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
@RequestMapping(value = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final Pipeline pipeline;
    private final CategoryRestMapper categoryRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<CategorySummaryResponse>> createCategory(
            @Valid @RequestBody CreateCategoryRequest request) {

        var command = request.toCommand();

        var responseDomain = command.execute(pipeline);

        var response = categoryRestMapper.toResponse(responseDomain);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<CategorySummaryResponse>builder()
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .data(response)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id) {
        var command = new DisableCategoryCommand(id);
        command.execute(pipeline);

        return ResponseEntity.noContent().build();

    }

}
