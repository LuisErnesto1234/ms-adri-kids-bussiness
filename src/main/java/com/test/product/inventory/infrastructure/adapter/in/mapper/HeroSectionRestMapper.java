package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.domain.model.HeroSection;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.herosection.HeroSectionSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HeroSectionRestMapper {

    HeroSectionSummaryResponse toResponse(HeroSection heroSection);

}
