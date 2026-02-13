package com.test.product.inventory.infrastructure.adapter;

import com.test.product.inventory.domain.model.HeroSection;
import com.test.product.inventory.domain.port.out.HeroSectionRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.out.persistence.mapper.HeroSectionEntityMapper;
import com.test.product.inventory.infrastructure.adapter.out.persistence.repository.HeroSectionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HeroSectionPersistenceAdapter implements HeroSectionRepositoryPort {

    private final HeroSectionJpaRepository heroSectionJpaRepository;
    private final HeroSectionEntityMapper heroSectionEntityMapper;

    @Override
    public HeroSection save(HeroSection heroSection) {
        var heroSectionEntity = heroSectionEntityMapper.toEntity(heroSection);
        var heroSectionSaved = heroSectionJpaRepository.save(heroSectionEntity);

        return heroSectionEntityMapper.toDomain(heroSectionSaved);
    }

    @Override
    public Optional<HeroSection> findById(UUID id) {
        return heroSectionJpaRepository.findById(id)
                .map(heroSectionEntityMapper::toDomain);
    }

    @Override
    public Page<HeroSection> findAll(Pageable pageable) {
        return heroSectionJpaRepository.findAll(pageable)
                .map(heroSectionEntityMapper::toDomain);
    }
}
