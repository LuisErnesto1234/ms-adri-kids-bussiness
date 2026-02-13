package com.adri.kids.inventory.infrastructure.adapter.in.mapper;

import com.adri.kids.inventory.domain.model.Color;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.color.ColorSummaryResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ColorRestMapper {

    ColorSummaryResponse toResponse(Color color);

}
