package com.test.product.inventory.application.querys.getcategoriesbyid;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.category.CategoryDetailResponse;

import java.util.UUID;

public record GetCategoryByIdQuery(UUID categoryId) implements Command<CategoryDetailResponse> {
}
