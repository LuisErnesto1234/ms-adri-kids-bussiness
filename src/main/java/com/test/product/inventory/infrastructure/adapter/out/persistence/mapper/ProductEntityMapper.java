package com.test.product.inventory.infrastructure.adapter.out.persistence.mapper;

import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.details.ProductDetails;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductEntityMapper {

    @Mapping(source = "categoryId", target = "category")
    ProductEntity toEntity(Product domain);

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
    ProductDetails toDetails(ProductEntity entity);


}
