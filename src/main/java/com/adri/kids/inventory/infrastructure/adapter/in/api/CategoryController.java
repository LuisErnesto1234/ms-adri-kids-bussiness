package com.adri.kids.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.adri.kids.inventory.application.command.category.activatecategory.ActivateCategoryCommand;
import com.adri.kids.inventory.application.command.category.deletecategory.DeleteCategoryCommand;
import com.adri.kids.inventory.application.querys.category.getcategories.GetCategoriesQuery;
import com.adri.kids.inventory.application.querys.category.getcategorybyid.GetCategoryByIdQuery;
import com.adri.kids.inventory.application.command.category.deactivatecategory.DeactivateCategoryCommand;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.category.CreateCategoryRequest;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.category.UpdateCategoryRequest;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.category.filter.CategoryFilterRequest;
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
@RequestMapping(path = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final Pipeline pipeline;
    private final CategoryRestMapper categoryRestMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryCardResponse>> createCategory(
            @Valid @RequestBody CreateCategoryRequest request) {
        var command = request.toCommand();
        var responseDomain = command.execute(pipeline);
        var response = categoryRestMapper.toResponse(responseDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildCreated(response));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<CategoryCardResponse>> updateCategory(@PathVariable UUID id,
                                                                            @Valid @RequestBody UpdateCategoryRequest request) {
        var command = request.toCommand(id);
        var result = command.execute(pipeline);
        var response = categoryRestMapper.toResponse(result);
        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }

    @GetMapping(path = "/find-all")
    public ResponseEntity<ApiResponse<PagedResult<CategoryCardResponse>>> findAllCategories(@PageableDefault Pageable pageable,
                                                                                            @ModelAttribute CategoryFilterRequest filterRequest) {
        var query = new GetCategoriesQuery(pageable.getPageNumber(), pageable.getPageSize(), filterRequest);

        var result = query.execute(pipeline);
        var responseCategory = result.getContent().stream().map(categoryRestMapper::toResponse).toList();
        var response = new PagedResult<>(responseCategory,
                result.getPage(), result.getSize(),
                result.getTotalElements(), result.getTotalPages());

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> findByIdCategory(@PathVariable UUID id) {
        var command = new GetCategoryByIdQuery(id);
        var result = command.execute(pipeline);
        var response = categoryRestMapper.toResponseDetail(result);

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }

    @PatchMapping(path = "/{id}/activate")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> activateCategory(@PathVariable UUID id) {
        var command = new ActivateCategoryCommand(id);
        var result = command.execute(pipeline);
        var response = categoryRestMapper.toResponseDetail(result);
        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }

    @PatchMapping(path = "/{id}/deactivate")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> deactivateCategory(@PathVariable UUID id) {
        var command = new DeactivateCategoryCommand(id);
        var result = command.execute(pipeline);
        var response = categoryRestMapper.toResponseDetail(result);
        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id) {
        var command = new DeleteCategoryCommand(id);
        command.execute(pipeline);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.buildNoContent("Categoria eliminada con exito"));
    }
}
