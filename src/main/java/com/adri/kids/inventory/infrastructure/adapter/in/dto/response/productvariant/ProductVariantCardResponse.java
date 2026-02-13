package com.adri.kids.inventory.infrastructure.adapter.in.dto.response.productvariant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.adri.kids.inventory.domain.enums.InventoryStatus;

import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ProductVariantCardResponse(
        UUID id,
        String sku,
        String colorName,   // Ej: "Rojo Fuego"
        String colorHex,    // Ej: "#FF0000" (Para pintar la bolita en el UI)
        String sizeName,    // Ej: "XL"
        BigDecimal price,   // Precio FINAL calculado (Base + Ajuste)
        Integer stock,      // Stock actual
        boolean isNew,      // <--- Tu requerimiento: ¿Esta variante es nueva?
        String imageUrl,    // Foto específica de este color
        InventoryStatus status
) implements Serializable {}