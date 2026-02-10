package com.test.product.inventory.infrastructure.adapter.out.persistence.repository;

import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ProductVariantEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductVariantJpaRepository extends JpaRepository<ProductVariantEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductVariantEntity p WHERE p.id = :id")
    Optional<ProductVariantEntity> findByIdWithLock(@Param("id") UUID id);

    @Query("SELECT pve FROM ProductVariantEntity pve")
    Page<ProductVariantEntity> findAllProductVariantWithColorWithSizes(Pageable pageable);
}
