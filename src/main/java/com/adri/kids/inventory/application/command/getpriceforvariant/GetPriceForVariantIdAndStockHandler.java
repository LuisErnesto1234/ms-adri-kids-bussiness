package com.adri.kids.inventory.application.command.getpriceforvariant;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.exception.InsufficientStockException;
import com.adri.kids.inventory.domain.port.out.ProductVariantRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class GetPriceForVariantIdAndStockHandler implements Command.Handler<GetPriceForVariantIdAndStockCommand, BigDecimal> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, timeout = 15)
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
