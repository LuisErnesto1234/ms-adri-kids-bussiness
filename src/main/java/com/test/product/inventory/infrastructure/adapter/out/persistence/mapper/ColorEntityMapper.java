package com.test.product.inventory.infrastructure.adapter.out.persistence.mapper;

import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ColorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ColorEntityMapper {

    ColorEntity toEntity(Color color);

    Color toDomain(ColorEntity entity);

}
