package com.adri.kids.inventory.infrastructure.adapter.out.persistence.repository;

import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.HeroSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HeroSectionJpaRepository extends JpaRepository<HeroSectionEntity, UUID> {
}
