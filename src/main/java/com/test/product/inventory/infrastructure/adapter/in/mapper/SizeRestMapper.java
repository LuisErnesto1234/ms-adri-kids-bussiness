package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.domain.model.Size;
import com.test.product.inventory.domain.port.in.command.size.CreateSizeCommand;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.SizeRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.SizeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SizeRestMapper {

    CreateSizeCommand toCommand(SizeRequest request);

    SizeResponse toResponse(Size size);

}
