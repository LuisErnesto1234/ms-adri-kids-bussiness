package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.application.usecases.createproduct.CreateProductCommand;
import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.model.details.ProductDetails;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.CreateProductRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductCardResponse;

import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {Instant.class, ChronoUnit.class, InventoryStatus.class})
public interface ProductRestMapper {

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "basePrice", target = "basePrice")
    CreateProductCommand toCommand(CreateProductRequest productRequest);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "variants", source = "productVariants")
    ProductDetailResponse toResponseDetails(ProductDetails productDetails);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "variantsCount", expression = "java(calculateVariantsCount(domain.productVariants()))")
    @Mapping(target = "isNew", expression = "java(calculateIsNew(domain.createdAt()))")
    ProductCardResponse toResponseCard(ProductDetails domain);

    // Lógica corregida: Contar solo los DISPONIBLES
    default Integer calculateVariantsCount(List<ProductVariant> variants) {
        if (variants == null) return 0;
        return (int) variants.stream()
                .filter(v -> v.status() == InventoryStatus.AVAILABLE) // Solo disponibles
                .count(); // Mucho más rápido que toList().size()
    }

    // Lógica corregida: Solo los creados hace menos de 7 días
    default Boolean calculateIsNew(Instant createdAt) {
        if (createdAt == null) return false;
        return createdAt.isAfter(Instant.now().minus(7, ChronoUnit.DAYS));
    }
}
