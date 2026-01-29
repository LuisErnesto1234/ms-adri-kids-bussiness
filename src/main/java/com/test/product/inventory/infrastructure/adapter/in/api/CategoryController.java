package com.test.product.inventory.infrastructure.adapter.in.api;

import com.test.product.inventory.domain.port.in.category_use_case.CreateCategoryUseCase;
import com.test.product.inventory.domain.port.in.command.category.CreateCategoryCommand;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.CategoryRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.CategoryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.CategoryRestMapper;
import com.test.product.inventory.infrastructure.utils.ApiResponse;
import com.test.product.inventory.infrastructure.utils.ConstantUtil;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(value = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final CategoryRestMapper categoryRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest request) {

        CreateCategoryCommand command = categoryRestMapper.toCommand(request);

        var category = createCategoryUseCase.createCategory(command);

        var categoryResponse = categoryRestMapper.toResponse(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<CategoryResponse>builder()
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .data(categoryResponse)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

}
