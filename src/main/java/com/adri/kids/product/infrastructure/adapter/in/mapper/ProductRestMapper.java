package com.adri.kids.product.infrastructure.adapter.in.mapper;

import com.adri.kids.product.application.product.command.CreateProductCommand;
import com.adri.kids.product.infrastructure.adapter.in.dto.response.ProductVariantCardResponse;
import com.adri.kids.shared.domain.enums.InventoryStatus;
import com.adri.kids.product.domain.model.Product;
import com.adri.kids.product.domain.model.ProductVariant;
import com.adri.kids.product.domain.model.details.ProductDetails;
import com.adri.kids.product.domain.model.details.ProductVariantDetails;
import com.adri.kids.product.infrastructure.adapter.in.dto.request.CreateProductRequest;
import com.adri.kids.product.infrastructure.adapter.in.dto.response.ProductCardResponse;

import com.adri.kids.product.infrastructure.adapter.in.dto.response.ProductDetailResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {Instant.class, ChronoUnit.class, InventoryStatus.class})
public interface ProductRestMapper {

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "productVariants", target = "variants")
    ProductDetailResponse toResponseDetails(ProductDetails productDetails);

    // El tipo de entrada aquí DEBE ser el mismo que el de la lista en ProductDetails
    @Mapping(target = "stock", source = "stockQuantity")
    @Mapping(target = "price", source = "priceAdjustment")
    @Mapping(target = "sizeName", source = "size.name")
    @Mapping(target = "colorName", source = "color.name")
    @Mapping(target = "colorHex", source = "color.hexCode")
    @Mapping(target = "isNew", ignore = true) // O tu lógica para calcularlo
    ProductVariantCardResponse toVariantResponse(ProductVariantDetails variant);

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "basePrice", target = "basePrice")
    CreateProductCommand toCommand(CreateProductRequest productRequest);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "variantsCount", expression = "java(calculateVariantsCount(domain.productVariants()))")
    @Mapping(target = "isNew", expression = "java(calculateIsNew(domain.createdAt()))")
    ProductCardResponse toResponseCard(ProductDetails domain);

    /**
     * Calcula el número de variantes disponibles desde ProductVariantDetails.
     *
     * @param variants lista de detalles de variantes
     * @return cantidad de variantes disponibles
     */
    default Integer calculateVariantsCount(List<ProductVariantDetails> variants) {
        if (variants == null) return 0;
        return (int) variants.stream()
                .filter(v -> v.status() == InventoryStatus.AVAILABLE)
                .count();
    }

    /**
     * Calcula el número de variantes disponibles desde ProductVariant.
     * Overload para soportar Product (que usa ProductVariant en lugar de ProductVariantDetails).
     *
     * @param variants lista de variantes de producto
     * @return cantidad de variantes disponibles
     */
    default Integer calculateVariantsCountFromProduct(List<ProductVariant> variants) {
        if (variants == null) return 0;
        return (int) variants.stream()
                .filter(v -> v.status() == InventoryStatus.AVAILABLE)
                .count();
    }

    default Boolean calculateIsNew(Instant createdAt) {
        if (createdAt == null) return false;
        return createdAt.isAfter(Instant.now().minus(7, ChronoUnit.DAYS));
    }

    @Mapping(target = "variantsCount", expression = "java(calculateVariantsCountFromProduct(product.productVariants()))")
    @Mapping(target = "isNew", expression = "java(calculateIsNew(product.createdAt()))")
    @Mapping(target = "categoryName", ignore = true)
    ProductCardResponse toResponseCard(Product product);
}
