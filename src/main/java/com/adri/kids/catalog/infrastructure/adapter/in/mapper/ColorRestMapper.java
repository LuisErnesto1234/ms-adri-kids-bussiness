package com.adri.kids.catalog.infrastructure.adapter.in.mapper;

import com.adri.kids.catalog.domain.model.Color;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.response.ColorSummaryResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ColorRestMapper {

    ColorSummaryResponse toResponse(Color color);

}
