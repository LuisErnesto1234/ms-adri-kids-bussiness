package com.test.product.orders.domain.model;

import java.math.BigDecimal;

public record PriceBreakdown(BigDecimal subTotal,       // Suma de items
                             BigDecimal discountAmount, // Cupones
                             BigDecimal shippingCost,   // Envío
                             BigDecimal tax,            // IGV / IVA (Opcional)
                             BigDecimal total)          // Lo que paga el cliente final)
{

    public static PriceBreakdown create(BigDecimal subTotal, BigDecimal discountAmount, BigDecimal shippingCost){
        BigDecimal tempTotal = subTotal.subtract(discountAmount).add(shippingCost);
        BigDecimal finalTotal = tempTotal.max(BigDecimal.ZERO);

        return new PriceBreakdown(subTotal, discountAmount, shippingCost, BigDecimal.ZERO, finalTotal);
    }

}
