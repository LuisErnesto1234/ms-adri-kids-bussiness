package com.test.product.inventory.infrastructure.adapter.out.persistence.mapper;

import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.details.ProductDetails;
import com.test.product.inventory.domain.model.details.ProductVariantDetails;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ProductEntity;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ProductVariantEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductEntityMapper {

    @Mapping(target = "isFeatured", source = "isFeatured")
    @Mapping(target = "variants", source = "productVariants")
    @Mapping(source = "countVariants", target = "variantCount")
    @Mapping(source = "categoryId", target = "category")
    ProductEntity toEntity(Product domain);

    @Mapping(target = "productVariants", source = "variants")
    @Mapping(source = "variantCount", target = "countVariants")
    @Mapping(source = "category.id", target = "categoryId")
    Product toDomain(ProductEntity entity);

    default CategoryEntity categoryIdToCategoryEntity(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        return categoryEntity;
    }

    @Mapping(source = "category", target = "category")
    @Mapping(source = "variants", target = "productVariants")
    ProductDetails toDetails(ProductEntity entity);

    @Mapping(source = "product", target = "product")
    @Mapping(source = "priceAdjustment", target = "priceAdjustment")
    ProductVariantDetails toProductVariantDetails(ProductVariantEntity variant);


}
