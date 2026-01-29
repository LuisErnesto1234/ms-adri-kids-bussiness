package com.test.product.customers.repository;

import com.test.product.customers.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {
}
