package com.test.product.orders.infrastructure.adapter.out.persistence.entity.embeddable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingDetailsEmbeddable {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "district", column = @Column(name = "shipping_district")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code")),
            @AttributeOverride(name = "reference", column = @Column(name = "shipping_reference"))
    })
    private AddressEntityEmbeddable address;

    private String carrier;

    private String trackingCode;

    private BigDecimal cost;

    private Instant estimatedDeliveryDate;
}