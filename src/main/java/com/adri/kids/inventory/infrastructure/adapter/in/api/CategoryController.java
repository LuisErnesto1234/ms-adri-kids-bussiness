package com.adri.kids.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.adri.kids.inventory.application.querys.getcategories.GetCategoriesQuery;
import com.adri.kids.inventory.application.querys.getcategoriesbyid.GetCategoryByIdQuery;
import com.adri.kids.inventory.application.command.disablecategory.DisableCategoryCommand;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.CreateCategoryRequest;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryCardResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryDetailResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.mapper.CategoryRestMapper;
import com.adri.kids.shared.domain.dtos.ApiResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final Pipeline pipeline;
    private final CategoryRestMapper categoryRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<CategoryCardResponse>> createCategory(
            @Valid @RequestBody CreateCategoryRequest request) {

        var command = request.toCommand();

        var responseDomain = command.execute(pipeline);

        var response = categoryRestMapper.toResponse(responseDomain);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildCreated(response));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id) {
        var command = new DisableCategoryCommand(id);
        command.execute(pipeline);

        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<ApiResponse<PagedResult<CategoryCardResponse>>> findAllCategories(@PageableDefault Pageable pageable,
                                                                                         @RequestParam(required = false) String search) {
        var query = new GetCategoriesQuery(pageable, search);

        var response = query.execute(pipeline);

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }

    @GetMapping(value = "/find-id/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> findByIdCategory(@PathVariable UUID id) {
        var command = new GetCategoryByIdQuery(id);

        var response = command.execute(pipeline);

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }
}
