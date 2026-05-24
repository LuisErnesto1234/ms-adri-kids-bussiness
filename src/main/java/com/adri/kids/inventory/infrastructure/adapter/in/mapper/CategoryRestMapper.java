package com.adri.kids.inventory.infrastructure.adapter.in.mapper;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryCardResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryDetailResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRestMapper {

    CategoryCardResponse toResponse(Category category);

    @Mapping(target = "id", source = "category.id")
    @Mapping(target = "name", source = "category.name")
    @Mapping(target = "descriptionShort", source = "category.descriptionShort")
    @Mapping(target = "urlImage", source = "category.urlImage")
    @Mapping(target = "createdAt", source = "category.createdAt")
    @Mapping(target = "updatedAt", source = "category.updatedAt")
    @Mapping(target = "status", source = "category.status")
    CategoryDetailResponse toResponseDetail(Category category, List<Product> products);

    CategoryDetailResponse toResponseDetail(Category category);
}
