package com.test.product.inventory.infrastructure.adapter.out.persistence.mapper;

import com.test.product.inventory.domain.model.HeroSection;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.HeroSectionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HeroSectionEntityMapper {

    HeroSection toDomain(HeroSectionEntity heroSectionEntity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HeroSectionEntity toEntity(HeroSection heroSection);

}
