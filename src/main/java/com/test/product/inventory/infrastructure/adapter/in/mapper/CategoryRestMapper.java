package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.domain.model.Category;
import com.test.product.inventory.application.usecases.createcategory.CreateCategoryCommand;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.CreateCategoryRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.CategorySummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRestMapper {

    CreateCategoryCommand toCommand(CreateCategoryRequest request);

    CategorySummaryResponse toResponse(Category category);

}
