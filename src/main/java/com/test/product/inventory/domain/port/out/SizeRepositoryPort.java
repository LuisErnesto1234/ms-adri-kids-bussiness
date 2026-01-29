package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.Size;

import java.util.Optional;
import java.util.UUID;

public interface SizeRepositoryPort {
    Size save(Size size);

    Optional<Size> findById(UUID id);

    Boolean existById(UUID id);
}
