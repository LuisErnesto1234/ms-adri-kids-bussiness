package com.adri.kids.catalog.infrastructure.adapter.out.persistence.repository;

import com.adri.kids.catalog.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID>,
        JpaSpecificationExecutor<CategoryEntity> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}
