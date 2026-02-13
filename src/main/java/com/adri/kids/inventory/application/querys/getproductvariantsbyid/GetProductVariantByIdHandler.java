package com.adri.kids.inventory.application.querys.getproductvariantsbyid;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.productvariant.ProductVariantDetailResponse;

import com.adri.kids.inventory.infrastructure.adapter.in.mapper.ProductVariantRestMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetProductVariantByIdHandler
        implements Command.Handler<GetProductVariantByIdQuery, ProductVariantDetailResponse> {

    private final ProductVariantRepositoryPort repositoryPort;
    private final ProductVariantRestMapper restMapper;

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, timeout = 10, propagation = Propagation.REQUIRED)
    @Cacheable(value = "product_variant", key = "'product_variant:' + #query.id", unless = "#result == null")
    @Override
    public ProductVariantDetailResponse handle(GetProductVariantByIdQuery query) {
        var productVariantFind = repositoryPort.findProductVariantDetailById(query.id())
                .orElseThrow(() -> new RuntimeException("product variant not found, para listar"));

        return restMapper.toResponseDetail(productVariantFind);
    }
}
