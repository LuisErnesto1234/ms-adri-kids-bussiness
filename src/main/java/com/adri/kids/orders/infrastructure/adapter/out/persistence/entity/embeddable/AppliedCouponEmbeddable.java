package com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppliedCouponEmbeddable {
    private String couponCode;
    private BigDecimal discountAmount;
}