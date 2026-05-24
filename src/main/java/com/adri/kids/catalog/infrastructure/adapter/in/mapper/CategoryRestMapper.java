package com.adri.kids.catalog.infrastructure.adapter.in.mapper;

import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.response.CategoryCardResponse;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.response.CategoryDetailResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRestMapper {

    CategoryCardResponse toResponse(Category category);

    CategoryDetailResponse toResponseDetail(Category category);
}
