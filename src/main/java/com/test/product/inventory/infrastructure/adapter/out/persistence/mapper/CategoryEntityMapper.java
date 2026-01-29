package com.test.product.inventory.infrastructure.adapter.out.persistence.mapper;

import com.test.product.inventory.domain.model.Category;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEntityMapper {

    Category toDomain(CategoryEntity entity);

    CategoryEntity toEntity(Category domain);

}
