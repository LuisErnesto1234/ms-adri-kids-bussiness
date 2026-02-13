package com.adri.kids.inventory.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InventoryStatus {
    AVAILABLE("Disponible"),
    OUT_OF_STOCK("Fuera de stock"),
    UNAVAILABLE("No disponible"),
    DISCONTINUED("Descontinuado");

    private final String value;
}
