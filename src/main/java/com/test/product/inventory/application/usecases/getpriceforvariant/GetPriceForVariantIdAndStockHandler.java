package com.test.product.inventory.application.usecases.getpriceforvariant;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.exception.InsufficientStockException;
import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class GetPriceForVariantIdAndStockHandler implements Command.Handler<GetPriceForVariantIdAndStockCommand, BigDecimal> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;

    @Override
    public BigDecimal handle(GetPriceForVariantIdAndStockCommand command) {
        var productVariant = productVariantRepositoryPort.findById(command.variantId())
                .orElseThrow(() -> new RuntimeException("El producto variante no ha sido encontrado con el id introducido"));

        if (productVariant.stockQuantity() < command.quantity()) {
            throw new InsufficientStockException("No hay suficiente stock, disponible para, " + productVariant.id());
        }

        return productVariant.priceAdjustment();
    }
}
