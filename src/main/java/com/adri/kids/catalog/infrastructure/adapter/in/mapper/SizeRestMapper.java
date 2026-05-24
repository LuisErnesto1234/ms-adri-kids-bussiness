package com.adri.kids.catalog.infrastructure.adapter.in.mapper;

import com.adri.kids.catalog.domain.model.Size;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.response.SizeSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SizeRestMapper {
    SizeSummaryResponse toResponse(Size size);
}
