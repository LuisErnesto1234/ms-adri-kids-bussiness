package com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntityEmbeddable {
    private String street;
    private String city;
    private String district;
    private String zipCode;
    private String reference;
}
