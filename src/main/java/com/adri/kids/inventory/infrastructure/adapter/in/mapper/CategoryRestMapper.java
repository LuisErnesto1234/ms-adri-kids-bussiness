package com.adri.kids.inventory.infrastructure.adapter.in.mapper;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryCardResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryDetailResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRestMapper {

    CategoryCardResponse toResponse(Category category);

    CategoryDetailResponse toResponseDetail(Category category);
}
