package com.test.product.inventory.application.querys.getproductbyid;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.product.ProductDetailResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductRestMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetProductByIdHandler implements Command.Handler<GetProductByIdQuery, ProductDetailResponse> {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductRestMapper productRestMapper;

    @Transactional(readOnly = true, timeout = 10, isolation = Isolation.REPEATABLE_READ)
    @Cacheable(
            value = "product", key = "'product:' + #query.id", unless = "#result == null"
    )
    @Override
    public ProductDetailResponse handle(GetProductByIdQuery query) {
        var productFind = productRepositoryPort.findProductDetailsById(query.id())
                .orElseThrow(() -> new RuntimeException("El producto no fue encontrado para su busqueda por id"));

        return productRestMapper.toResponseDetails(productFind);
    }
}
