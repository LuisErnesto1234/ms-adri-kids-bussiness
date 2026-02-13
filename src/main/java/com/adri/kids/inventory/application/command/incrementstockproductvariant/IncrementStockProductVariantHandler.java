package com.adri.kids.inventory.application.command.incrementstockproductvariant;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.application.command.decrementproductvariantstock.DecrementStockProductVariantCommand;
import com.adri.kids.inventory.domain.exception.NotFoundException;
import com.adri.kids.inventory.domain.model.ProductVariant;
import com.adri.kids.inventory.domain.port.out.ProductVariantRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class IncrementStockProductVariantHandler implements Command.Handler<IncrementStockProductVariantCommand, Void> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 5)
    @Retryable(retryFor = {ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 200))
    @Override
    public Void handle(IncrementStockProductVariantCommand command) {
        try {
            ProductVariant productVariantFind = productVariantRepositoryPort.findById(command.productVariantId())
                    .orElseThrow(() -> new NotFoundException("El producto no fue encontrado, para su incremento de stock"));

            productVariantFind.increaseStock(command.quantity());

            productVariantRepositoryPort.save(productVariantFind);

            return null;
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new NotFoundException("El stock fue modificado por otro usuario mientras comprabas. Por favor, intenta de nuevo.", e);
        }
    }

    @Recover
    public ProductVariant recover(ObjectOptimisticLockingFailureException e, DecrementStockProductVariantCommand command){
        throw new NotFoundException("El sistema está muy ocupado, no pudimos actualizar el stock tras varios intentos.", e);
    }
}
