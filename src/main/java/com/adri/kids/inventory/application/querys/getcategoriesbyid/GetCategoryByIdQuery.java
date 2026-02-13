package com.adri.kids.inventory.application.querys.getcategoriesbyid;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryDetailResponse;

import java.util.UUID;

public record GetCategoryByIdQuery(UUID categoryId) implements Command<CategoryDetailResponse> {
}
