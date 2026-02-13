package com.adri.kids.inventory.infrastructure.adapter.out.persistence.mapper;

import com.adri.kids.inventory.domain.model.Size;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.SizeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SizeEntityMapper {


    SizeEntity toEntity(Size domain);

    Size toDomain(SizeEntity entity);

}
