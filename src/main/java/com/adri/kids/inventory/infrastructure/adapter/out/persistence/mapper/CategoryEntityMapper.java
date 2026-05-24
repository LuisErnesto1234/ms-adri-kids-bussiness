package com.adri.kids.inventory.infrastructure.adapter.out.persistence.mapper;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEntityMapper {

    @Mapping(target = "colorCode", source = "colorCodeOfTheme.id")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "status", source = "status")
    Category toDomain(CategoryEntity entity);

    @Mapping(target = "colorCodeOfTheme", ignore = true)
    @Mapping(target = "status", source = "status")
    @Mapping(target = "productEntities", source = "products")
    CategoryEntity toEntity(Category domain);

}
