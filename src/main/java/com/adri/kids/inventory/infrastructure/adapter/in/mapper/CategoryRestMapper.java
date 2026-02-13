package com.adri.kids.inventory.infrastructure.adapter.in.mapper;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.application.command.createcategory.CreateCategoryCommand;
import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.CreateCategoryRequest;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.product.ProductCardResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryCardResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryDetailResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRestMapper {

    CreateCategoryCommand toCommand(CreateCategoryRequest request);

    CategoryCardResponse toResponse(Category category);

    @Mapping(target = "id", source = "category.id")
    @Mapping(target = "name", source = "category.name")
    @Mapping(target = "description", source = "category.description")
    @Mapping(target = "urlImage", source = "category.urlImage")
    @Mapping(target = "createdAt", source = "category.createdAt")
    @Mapping(target = "updatedAt", source = "category.updatedAt")
    @Mapping(target = "status", source = "category.generalStatus")
    // Pasamos 'products' a 'productCards' Y ADEMÁS pasamos la categoría como contexto
    @Mapping(target = "productCards", source = "products")
    CategoryDetailResponse toResponseDetail(Category category, List<Product> products);

    // 2. Método para la lista (MapStruct lo usa automáticamente para convertir la lista)
    // Usamos @Context para pasar el objeto Category hacia abajo
    default List<ProductCardResponse> toProductCardResponses(List<Product> products, @Context Category category) {
        return products.stream()
                .map(product -> toProductCardResponse(product, category))
                .toList();
    }

    // 3. Método individual
    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "basePrice", source = "product.basePrice")
    @Mapping(target = "imageUrl", source = "product.imageUrl")
    @Mapping(target = "status", source = "product.status")
    // Usamos el contador que viene de la @Formula (sin calcular size() de lista nula)
    @Mapping(target = "variantsCount", source = "product.countVariants")
    // Mapeamos el nombre desde el contexto
    @Mapping(target = "categoryName", ignore = true)
    ProductCardResponse toProductCardResponse(Product product, @Context Category category);

}
