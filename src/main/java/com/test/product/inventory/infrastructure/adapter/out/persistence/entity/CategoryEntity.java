package com.test.product.inventory.infrastructure.adapter.out.persistence.entity;

import com.test.product.inventory.domain.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryEntity {

    @Id
    private UUID id;

    @Column(name = "name", length = 200, unique = true)
    private String name;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 500)
    private String urlImage;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductEntity> productEntities;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InventoryStatus status;

}
