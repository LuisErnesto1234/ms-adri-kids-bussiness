package com.test.product.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

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

    private final Pipeline pipeline;
    private final CategoryRestMapper categoryRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryRequest request) {

        var command = request.toCommand();

        var responseDomain = command.execute(pipeline);

        var response = categoryRestMapper.toResponse(responseDomain);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<CategoryResponse>builder()
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .data(response)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

}
