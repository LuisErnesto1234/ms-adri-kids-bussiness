package com.test.product.inventory.infrastructure.adapter.out.persistence.repository;

import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = "SELECT p FROM ProductEntity p JOIN FETCH p.category",
    countQuery = "SELECT COUNT(p) FROM ProductEntity p")
    Page<ProductEntity> findAllWithCategory(Pageable pageable);

}
