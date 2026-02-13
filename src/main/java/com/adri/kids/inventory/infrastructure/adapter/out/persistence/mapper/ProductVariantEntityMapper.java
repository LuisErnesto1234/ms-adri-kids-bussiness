package com.adri.kids.inventory.infrastructure.adapter.out.persistence.mapper;

import com.adri.kids.inventory.domain.model.ProductVariant;
import com.adri.kids.inventory.domain.model.details.ProductVariantDetails;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.ColorEntity;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.ProductEntity;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.ProductVariantEntity;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.SizeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductVariantEntityMapper {

    // --- ENTITY A DOMAIN ---
    // Extraemos los IDs de los objetos relacionados
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "colorId", source = "color.id")
    @Mapping(target = "sizeId", source = "size.id")
    ProductVariant toDomain(ProductVariantEntity entity);

    // --- DOMAIN A ENTITY ---
    @Mapping(target = "version", ignore = true)
    // Convertimos los UUIDs del dominio a Entidades (Referencias)
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductRef")
    @Mapping(target = "color", source = "colorId", qualifiedByName = "mapColorRef")
    @Mapping(target = "size", source = "sizeId", qualifiedByName = "mapSizeRef")
    ProductVariantEntity toEntity(ProductVariant domain);

    @Mapping(target = "product", source = "product")
    @Mapping(target = "stockQuantity", source = "stockQuantity")
    @Mapping(target = "product.productVariants", source = "product.variants")
    @Mapping(target = "product.categoryId", source = "product.category.id")
    @Mapping(target = "product.countVariants", ignore = true)
    ProductVariantDetails toDetails(ProductVariantEntity entity);


    // --- MÉTODOS AUXILIARES ---
    // Estos métodos crean una "referencia" (objeto vacío con solo ID) para que Hibernate pueda guardar la FK

    @Named("mapProductRef")
    default ProductEntity mapProductRef(UUID id) {
        return id == null ? null : ProductEntity.builder().id(id).build();
    }

    @Named("mapColorRef")
    default ColorEntity mapColorRef(UUID id) {
        return id == null ? null : ColorEntity.builder().id(id).build();
    }

    @Named("mapSizeRef")
    default SizeEntity mapSizeRef(UUID id) {
        return id == null ? null : SizeEntity.builder().id(id).build();
    }
}