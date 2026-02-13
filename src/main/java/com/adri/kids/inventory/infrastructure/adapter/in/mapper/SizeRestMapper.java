package com.adri.kids.inventory.infrastructure.adapter.in.mapper;

import com.adri.kids.inventory.domain.model.Size;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.size.SizeSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SizeRestMapper {
    SizeSummaryResponse toResponse(Size size);
}
