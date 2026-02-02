package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.model.details.ProductVariantDetails;
import com.test.product.inventory.domain.port.in.command.product_variant.CreateProductVariantCommand;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.ProductVariantRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductVariantRestMapper {

    CreateProductVariantCommand toCommand(ProductVariantRequest productVariantRequest);

    ProductVariantResponse toResponse(ProductVariantDetails productVariantDetails);
}
