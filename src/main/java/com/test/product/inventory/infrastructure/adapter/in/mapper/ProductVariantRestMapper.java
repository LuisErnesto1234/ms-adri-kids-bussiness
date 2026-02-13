package com.test.product.inventory.infrastructure.adapter.in.mapper;

import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.model.details.ProductDetails;
import com.test.product.inventory.domain.model.details.ProductVariantDetails;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.product.ProductDetailResponse;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.productvariant.ProductVariantCardResponse;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.productvariant.ProductVariantDetailResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {Instant.class, ChronoUnit.class})
public interface ProductVariantRestMapper {

    ProductVariantCardResponse toResponseCard(ProductVariant productVariant);

    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "color.name", target = "colorName")
    @Mapping(source = "size.name", target = "sizeName")
    @Mapping(source = "color.hexCode", target = "colorHexCode")
    ProductVariantDetailResponse toResponseDetail(ProductVariantDetails productVariantDetails);

    @Mapping(target = "variants", source = "productVariants")
        // MapStruct usará el método de abajo
    ProductDetailResponse toDetail(ProductDetails product);

    // Mapeo de la Lista de Variantes
    // Pasamos el "product" entero como contexto para acceder a su precio base
    default List<ProductVariantCardResponse> mapVariants(List<ProductVariantDetails> variants, @Context ProductDetails product) {
        if (variants == null) return Collections.emptyList();

        return variants.stream()
                .map(v -> toResponseDetails(v, product.basePrice())) // Pasamos el precio base
                .toList();
    }

    // CORRECCIÓN 1: Usamos @Context para basePrice.
    // Esto permite usar "color.name" directamente (asumiendo que viene de 'details')
    // CORRECCIÓN 2: En 'expression', usamos los nombres reales de los parámetros ('details' y 'basePrice')

    @Mapping(source = "color.name", target = "colorName")
    @Mapping(source = "color.hexCode", target = "colorHex")
    @Mapping(source = "size.name", target = "sizeName")
    @Mapping(target = "sku", source = "sku") // Ojo: Tu record tiene 'sku' (Mayúscula) y el DTO 'sku'
    @Mapping(target = "price", expression = "java(calculateFinalPrice(basePrice, details.priceAdjustment()))")
    @Mapping(target = "isNew", expression = "java(isVariantNew(details.createdAt()))")
    @Mapping(target = "stock", source = "stockQuantity")
        // Mapeo de stockQuantity -> stock
    ProductVariantCardResponse toResponseDetails(ProductVariantDetails details, @Context BigDecimal basePrice);

    // --- Métodos Default ---

    default BigDecimal calculateFinalPrice(BigDecimal base, BigDecimal adjustment) {
        if (adjustment == null) return base;
        return base.add(adjustment);
    }

    default boolean isVariantNew(Instant createdAt) {
        if (createdAt == null) return false;
        return createdAt.isAfter(Instant.now().minus(15, ChronoUnit.DAYS));
    }
}
