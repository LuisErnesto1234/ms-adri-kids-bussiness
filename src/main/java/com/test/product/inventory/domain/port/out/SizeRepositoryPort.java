package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface SizeRepositoryPort {
    Size save(Size size);

    Optional<Size> findById(UUID id);

    Boolean existById(UUID id);

    Page<Size> findAll(Pageable pageable);
}
