package com.test.product.inventory.infrastructure.adapter.out.persistence.entity;

import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.enums.TypeProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Table(name = "sizes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SizeEntity {
    @Id
    private UUID id;
    
    @Column(name = "name", length = 100, unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeProduct type;
    
    @Column(name = "sort_order")
    private String sortOrder;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InventoryStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SizeEntity that = (SizeEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
