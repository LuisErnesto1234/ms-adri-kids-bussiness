package com.test.product.inventory.application.usecases.decrementproductvariantstock;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DecrementStockProductVariantHandler implements Command.Handler<DecrementStockProductVariantCommand, ProductVariant> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;

    @Override
    public ProductVariant handle(DecrementStockProductVariantCommand command) {
        try {
            ProductVariant productVariantFind = productVariantRepositoryPort.findById(command.productVariantId())
                    .orElseThrow(() -> new NotFoundException("El producto no fue encontrado, para su decremento de stock"));

            productVariantFind.decreaseStock(command.stock());

            return productVariantRepositoryPort.save(productVariantFind);
        } catch (ObjectOptimisticLockingFailureException e){
            throw new NotFoundException("El stock ha sido modificado por otro usuario mientras comprabas. Por favor, intenta de nuevo");
        }
    }
}
