package com.test.product.inventory.application.querys.getcategoriesbyid;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;
import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.category.CategoryDetailResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.CategoryRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetCategoryByIdHandler implements Command.Handler<GetCategoryByIdQuery, CategoryDetailResponse> {

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final CategoryRestMapper categoryRestMapper;
    private final ProductRepositoryPort productRepositoryPort;

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, timeout = 10)
    @Cacheable(value = "category", key = "'category:' + #query.categoryId", unless = "#result == null")
    @Override
    public CategoryDetailResponse handle(GetCategoryByIdQuery query) {
        var category = categoryRepositoryPort.findById(query.categoryId())
                .orElseThrow(() -> new RuntimeException("La categoria no fue encontrada, para mostrar la por id"));

        List<Product> products = productRepositoryPort.findAllByCategory(query.categoryId());

        return categoryRestMapper.toResponseDetail(category, products);
    }

}
