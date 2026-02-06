package com.test.product.orders.domain.model;

import com.test.product.orders.domain.enums.DiscountType;

import java.math.BigDecimal;
import java.util.UUID;

public record AppliedCoupon(UUID id,             // El ID original del cupón
                            String code,         // Ej: "VERANO2026"
                            DiscountType type,   // PERCENTAGE o FIXED
                            BigDecimal value) {     // Ej: 10 (para 10%) o 50 (para $50)

    public BigDecimal calculateDiscount(BigDecimal subTotal) {
        if (type == DiscountType.FIXED_AMOUNT) {
            return value.min(subTotal);
        } else {
            // Cálculo de porcentaje: subTotal * (value / 100)
            return subTotal.multiply(value)
                    .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
        }
    }
}
