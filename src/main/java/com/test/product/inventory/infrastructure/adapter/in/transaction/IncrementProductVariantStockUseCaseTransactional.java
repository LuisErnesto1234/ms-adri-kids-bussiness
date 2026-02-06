package com.test.product.inventory.infrastructure.adapter.in.transaction;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.application.usecases.incrementstockproductvariant.IncrementStockProductVariantCommand;
import com.test.product.inventory.domain.port.in.product_variant_use_case.IncrementProductVariantStockUseCase;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Primary
@Component
@RequiredArgsConstructor
public class IncrementProductVariantStockUseCaseTransactional {

    private final IncrementProductVariantStockUseCase incrementProductVariantStockUseCase;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    // Configuración:
    // 1. retryFor: Qué excepción dispara el reintento (Bloqueo Optimista).
    // 2. maxAttempts: Cuántas veces probará en total (ej. 1 intento inicial + 2 reintentos).
    // 3. backoff: Tiempo de espera entre intentos (100ms) para dar tiempo a que la otra transacción termine.
    @Retryable(retryFor = {ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class},
    maxAttempts = 3,
    backoff = @Backoff(delay = 200))
    public void incrementProductVariantStock(IncrementStockProductVariantCommand command){
        incrementProductVariantStockUseCase.incrementProductVariantStock(command);
    }

    @Recover
    public void recover(ObjectOptimisticLockingFailureException e, UUID variantId, Integer quantityToIncrement){
        throw new NotFoundException("El sistema está muy ocupado, no pudimos actualizar el stock tras varios intentos.", e);
    }

}
