package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.Color;

import java.util.Optional;
import java.util.UUID;

public interface ColorRepositoryPort {

    Color save(Color color);
    Optional<Color> findById(UUID id);
    Boolean existById(UUID id);
    void deleteById(UUID id);

}
