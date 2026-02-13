package com.adri.kids.orders.domain.model;

public record Address(
        String street,
        String city,
        String district,
        String zipCode,
        String reference
) {
    public String fullAddress() {
        return "%s, %s, %s - (%s)".formatted(street, district, city, zipCode);
    }
}