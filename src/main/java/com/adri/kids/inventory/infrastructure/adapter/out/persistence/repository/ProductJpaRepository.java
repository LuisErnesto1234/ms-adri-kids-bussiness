package com.adri.kids.inventory.infrastructure.adapter.out.persistence.repository;

import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = "SELECT p FROM ProductEntity p JOIN FETCH p.category",
    countQuery = "SELECT COUNT(p) FROM ProductEntity p")
    Page<ProductEntity> findAllWithCategory(Pageable pageable);

    // Agregamos LEFT JOIN FETCH y DISTINCT
    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :categoryId")
    List<ProductEntity> findAllByCategoryId(@Param("categoryId") UUID categoryId);

    @Query("SELECT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.variants v " +
            "LEFT JOIN FETCH v.color " +
            "LEFT JOIN FETCH v.size " +
            "WHERE p.id = :id")
    Optional<ProductEntity> findProductDetailsById(@Param("id") UUID id);

}
