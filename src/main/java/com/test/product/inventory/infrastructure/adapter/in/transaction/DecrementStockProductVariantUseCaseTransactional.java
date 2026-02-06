package com.test.product.inventory.infrastructure.adapter.in.transaction;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.application.usecases.decrementproductvariantstock.DecrementStockProductVariantCommand;
import com.test.product.inventory.domain.port.in.product_variant_use_case.DecrementStockProductVariantUseCase;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Component
@RequiredArgsConstructor
public class DecrementStockProductVariantUseCaseTransactional {

    private final DecrementStockProductVariantUseCase decrementStockProductVariantUseCase;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 5)
    @Retryable(retryFor = {ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class},
    maxAttempts = 3,
    backoff = @Backoff(delay = 200))
    public ProductVariant decrementProductVariantStock(DecrementStockProductVariantCommand command) {
        return decrementStockProductVariantUseCase.decrementProductVariantStock(command);
    }

    @Recover
    public ProductVariant recover(ObjectOptimisticLockingFailureException e, DecrementStockProductVariantCommand command){
        throw new NotFoundException("El sistema está muy ocupado, no pudimos actualizar el stock tras varios intentos.", e);
    }

}
