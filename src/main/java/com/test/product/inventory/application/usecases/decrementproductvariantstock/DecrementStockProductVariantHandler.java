package com.test.product.inventory.application.usecases.decrementproductvariantstock;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DecrementStockProductVariantHandler implements Command.Handler<DecrementStockProductVariantCommand, ProductVariant> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 15, isolation = Isolation.READ_COMMITTED)
    @Retryable(retryFor = {ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 200))
    @Caching(evict = {
            @CacheEvict(value = "product_variant", key = "'product_variant:' + #command.productVariantId()"),
            @CacheEvict(value = "product_variant_page", allEntries = true)
    })
    @Override
    public ProductVariant handle(DecrementStockProductVariantCommand command) {
        try {
            ProductVariant productVariantFind = productVariantRepositoryPort.findById(command.productVariantId())
                    .orElseThrow(() -> new NotFoundException("El producto no fue encontrado, para su decremento de stock"));

            productVariantFind.decreaseStock(command.stock());

            return productVariantRepositoryPort.save(productVariantFind);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new NotFoundException("El stock ha sido modificado por otro usuario mientras comprabas. Por favor, intenta de nuevo");
        }
    }

    @Recover
    public ProductVariant recover(ObjectOptimisticLockingFailureException e, DecrementStockProductVariantCommand command) {
        throw new NotFoundException("El sistema está muy ocupado, no pudimos actualizar el stock tras varios intentos.", e);
    }
}
