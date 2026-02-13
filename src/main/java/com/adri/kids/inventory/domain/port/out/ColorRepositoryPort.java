package com.adri.kids.inventory.domain.port.out;

import com.adri.kids.inventory.domain.model.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ColorRepositoryPort {

    Color save(Color color);
    Optional<Color> findById(UUID id);
    Boolean existById(UUID id);
    void deleteById(UUID id);

    Page<Color> findAll(Pageable pageable);

}
