package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.HeroSection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface HeroSectionRepositoryPort {

    HeroSection save(HeroSection heroSection);

    Optional<HeroSection> findById(UUID id);

    Page<HeroSection> findAll(Pageable pageable);
}
