package com.test.product.inventory.infrastructure.adapter.out.persistence.mapper;

import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ProductVariantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductVariantEntityMapper {

    ProductVariant toDomain(ProductVariantEntity entity);

    ProductVariantEntity toEntity(ProductVariant domain);

}
