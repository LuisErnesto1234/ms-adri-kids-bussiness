package com.test.product.inventory.infrastructure.adapter.out.persistence.repository;

import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColorJpaRepository extends JpaRepository<ColorEntity, UUID> {
}
