package com.test.product.inventory.infrastructure.config.beans;

import com.test.product.inventory.application.service.*;
import com.test.product.inventory.domain.port.in.category_use_case.CreateCategoryUseCase;
import com.test.product.inventory.domain.port.in.color_use_case.CreateColorUseCase;
import com.test.product.inventory.domain.port.in.product_use_case.CreateProductUseCase;
import com.test.product.inventory.domain.port.in.size.CreateSizeUseCase;
import com.test.product.inventory.domain.port.out.*;
import com.test.product.inventory.infrastructure.adapter.in.transaction.DecrementStockProductVariantUseCaseTransactional;
import com.test.product.inventory.infrastructure.adapter.in.transaction.IncrementProductVariantStockUseCaseTransactional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateCategoryUseCase createCategoryUseCase(CategoryRepositoryPort categoryRepositoryPort) {
        return new CategoryService(categoryRepositoryPort);
    }

    @Bean
    public CategoryService categoryService(CategoryRepositoryPort categoryRepositoryPort) {
        return new CategoryService(categoryRepositoryPort);
    }

    @Bean
    public CreateColorUseCase createColorUseCase(ColorRepositoryPort colorRepositoryPort) {
        return new ColorService(colorRepositoryPort);
    }

    @Bean
    public ColorService colorService(ColorRepositoryPort colorRepositoryPort) {
        return new ColorService(colorRepositoryPort);
    }

    @Bean
    public CreateProductUseCase createProductUseCase(ProductRepositoryPort productRepositoryPort, CategoryRepositoryPort categoryRepositoryPort) {
        return new ProductService(productRepositoryPort, categoryRepositoryPort);
    }

    @Bean
    public ProductService productService(ProductRepositoryPort productRepositoryPort, CategoryRepositoryPort categoryRepositoryPort) {
        return new ProductService(productRepositoryPort, categoryRepositoryPort);
    }

    @Bean
    public ProductVariantService productVariantService(ProductVariantRepositoryPort productVariantRepositoryPort,
                                                       ProductRepositoryPort productRepositoryPort,
                                                       ColorRepositoryPort colorRepositoryPort,
                                                       SizeRepositoryPort sizeRepositoryPort
    ) {
        return new ProductVariantService(productVariantRepositoryPort, productRepositoryPort,
                colorRepositoryPort, sizeRepositoryPort);
    }

    @Bean
    public CreateSizeUseCase createSizeUseCase(SizeRepositoryPort sizeRepositoryPort) {
        return new SizeService(sizeRepositoryPort);
    }

    @Bean
    public SizeService sizeService(SizeRepositoryPort sizeRepositoryPort) {
        return new SizeService(sizeRepositoryPort);
    }

    @Bean
    public IncrementProductVariantStockUseCaseTransactional incrementProductVariantStockUseCase(
            ProductVariantService productVariantService) {
        return new IncrementProductVariantStockUseCaseTransactional(productVariantService);
    }

    @Bean
    public DecrementStockProductVariantUseCaseTransactional decrementStockProductVariantUseCaseTransactional(
            ProductVariantService productVariantService) {
        return new DecrementStockProductVariantUseCaseTransactional(productVariantService);
    }
}
