package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.application.usecases.createcolor.CreateColorCommand;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.ColorRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ColorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ColorRestMapper {

    CreateColorCommand toCommand(ColorRequest colorRequest);

    ColorResponse toResponse(Color color);

}
