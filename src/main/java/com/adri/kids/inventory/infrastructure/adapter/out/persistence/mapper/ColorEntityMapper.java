package com.adri.kids.inventory.infrastructure.adapter.out.persistence.mapper;

import com.adri.kids.inventory.domain.model.Color;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.ColorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ColorEntityMapper {

    ColorEntity toEntity(Color color);

    Color toDomain(ColorEntity entity);

}
