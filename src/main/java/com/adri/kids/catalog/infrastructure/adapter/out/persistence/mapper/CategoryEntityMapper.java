package com.adri.kids.catalog.infrastructure.adapter.out.persistence.mapper;

import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.infrastructure.adapter.out.persistence.entity.CategoryEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEntityMapper {

    @Mapping(target = "isShowMainMenu", source = "showMainMenu")
    @Mapping(target = "products", source = "productEntities")
    Category toDomain(CategoryEntity entity);

    @Mapping(target = "productEntities", source = "products")
    CategoryEntity toEntity(Category domain);
}
