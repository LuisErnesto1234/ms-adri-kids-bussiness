package com.test.product.inventory.application.usecases.incrementstockproductvariant;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncrementStockProductVariantHandler implements Command.Handler<IncrementStockProductVariantCommand, Void> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;

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
}
