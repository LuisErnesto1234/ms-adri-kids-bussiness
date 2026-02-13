package com.adri.kids.inventory.application.command.updateproduct;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.port.out.ProductRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateProductHandler implements Command.Handler<UpdateProductCommand, UUID> {

    private final ProductRepositoryPort productRepositoryPort;

    @Transactional(rollbackFor = Exception.class, timeout = 20, propagation = Propagation.MANDATORY,
            isolation = Isolation.READ_COMMITTED)
    @Caching(evict = {
            @CacheEvict(value = "products_page", allEntries = true, condition = "#result != null"),
            @CacheEvict(value = "product", key = "'product:' + #result", condition = "#result != null")
    })
    @Override
    public UUID handle(UpdateProductCommand command) {

        var productFind = productRepositoryPort.findById(command.productId())
                .orElseThrow(() -> new RuntimeException("El producto no fue encontrado, para su actualización"));

        var productToSave = productFind.update(command.categoryId(), command.name(), command.description(), command.basePrice(),
                command.imageUrl(), command.isFeatured(), command.status());

        return productRepositoryPort.save(productToSave).id();
    }
}
