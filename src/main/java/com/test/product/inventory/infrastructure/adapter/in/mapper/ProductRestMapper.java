package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.domain.model.details.ProductDetails;
import com.test.product.inventory.domain.port.in.command.product.CreateProductCommand;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.ProductRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CategoryRestMapper.class})
public interface ProductRestMapper {

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "basePrice", target = "basePrice")
    CreateProductCommand toCommand(ProductRequest productRequest);

    @Mapping(source = "category", target = "categoryResponse")
    @Mapping(source = "productVariants", target = "productVariants")
    ProductResponse toResponse(ProductDetails productDetails);

}
