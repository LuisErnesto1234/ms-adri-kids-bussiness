package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.domain.model.Size;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.SizeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SizeRestMapper {
    SizeResponse toResponse(Size size);
}
