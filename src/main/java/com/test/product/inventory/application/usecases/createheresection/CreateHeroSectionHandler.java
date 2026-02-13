package com.test.product.inventory.application.usecases.createheresection;

import org.springframework.cache.annotation.CacheEvict;
import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.model.HeroSection;
import com.test.product.inventory.domain.port.out.HeroSectionRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateHeroSectionHandler implements Command.Handler<CreateHeroSectionCommand, UUID> {

    private final HeroSectionRepositoryPort heroSectionRepositoryPort;

    @Transactional(rollbackFor = Exception.class, timeout = 15, propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    @Caching(evict = {
            @CacheEvict(value = "hero_section_page", allEntries = true, condition = "#result != null"),
            @CacheEvict(value = "hero_section", key = "'hero_section:' + #result", condition = "#result != null")
    })
    @Override
    public UUID handle(CreateHeroSectionCommand command) {

        var heroSection = HeroSection.create(command.title(), command.subtitle(), command.ctaText(),
                command.ctaUrl(), command.imageUrls(), command.status());

        return heroSectionRepositoryPort.save(heroSection).id();
    }
}
