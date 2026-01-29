package com.test.product.inventory.infrastructure.adapter;

import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ColorEntity;
import com.test.product.inventory.infrastructure.adapter.out.persistence.mapper.ColorEntityMapper;
import com.test.product.inventory.infrastructure.adapter.out.persistence.repository.ColorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ColorPersistenceAdapter implements ColorRepositoryPort {

    private final ColorJpaRepository colorJpaRepository;
    private final ColorEntityMapper colorEntityMapper;

    @Override
    public Color save(Color color) {

        ColorEntity colorSave = colorJpaRepository.save(colorEntityMapper.toEntity(color));

        return colorEntityMapper.toDomain(colorSave);
    }

    @Override
    public Optional<Color> findById(UUID id) {
        return colorJpaRepository.findById(id).map(colorEntityMapper::toDomain);
    }

    @Override
    public Boolean existById(UUID id) {
        return colorJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        colorJpaRepository.deleteById(id);
    }
}
