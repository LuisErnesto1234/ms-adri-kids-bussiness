package com.adri.kids.shared.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeProduct {
    T_SHIRT("Polo"),
    PANT("Pantalón"),
    COAT("Casaca");

    private final String value;
}
