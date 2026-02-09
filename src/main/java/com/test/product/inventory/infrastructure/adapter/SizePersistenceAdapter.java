package com.test.product.inventory.infrastructure.adapter;

import com.test.product.inventory.domain.model.Size;
import com.test.product.inventory.domain.port.out.SizeRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.SizeEntity;
import com.test.product.inventory.infrastructure.adapter.out.persistence.mapper.SizeEntityMapper;
import com.test.product.inventory.infrastructure.adapter.out.persistence.repository.SizeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SizePersistenceAdapter implements SizeRepositoryPort {

    private final SizeJpaRepository sizeJpaRepository;
    private final SizeEntityMapper sizeEntityMapper;

    @Override
    public Size save(Size size) {
        SizeEntity sizeEntitySave = sizeJpaRepository.save(sizeEntityMapper.toEntity(size));
        return sizeEntityMapper.toDomain(sizeEntitySave);
    }

    @Override
    public Optional<Size> findById(UUID id) {
        return sizeJpaRepository.findById(id).map(sizeEntityMapper::toDomain);
    }

    @Override
    public Boolean existById(UUID id) {
        return sizeJpaRepository.existsById(id);
    }

    @Override
    public Page<Size> findAll(Pageable pageable) {

        var sizeEntityPage = sizeJpaRepository.findAll(pageable);

        return sizeEntityPage.map(sizeEntityMapper::toDomain);
    }
}
