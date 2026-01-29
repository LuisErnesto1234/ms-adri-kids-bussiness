package com.test.product.customers.dto.request;

import com.test.product.customers.dto.enums.AddressType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AddressRequest(UUID customerId, String street, String city, String state,
                             String postalCode, String country, AddressType type) {
}
