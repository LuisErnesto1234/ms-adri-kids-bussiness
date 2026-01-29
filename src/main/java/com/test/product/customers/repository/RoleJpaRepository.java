package com.test.product.customers.repository;

import com.test.product.customers.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
}
