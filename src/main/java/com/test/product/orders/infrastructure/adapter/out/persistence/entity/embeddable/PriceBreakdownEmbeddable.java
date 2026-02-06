package com.test.product.orders.infrastructure.adapter.out.persistence.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceBreakdownEmbeddable {
    private BigDecimal subTotal;
    private BigDecimal discount;
    private BigDecimal shippingCost;
    private BigDecimal total;
}