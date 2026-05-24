package com.adri.kids.content.infrastructure.adapter.in.mapper;

import com.adri.kids.content.domain.model.HeroSection;
import com.adri.kids.content.infrastructure.adapter.in.dto.response.HeroSectionSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HeroSectionRestMapper {

    HeroSectionSummaryResponse toResponse(HeroSection heroSection);

}
