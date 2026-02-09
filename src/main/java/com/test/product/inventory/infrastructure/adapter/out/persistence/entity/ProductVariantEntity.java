package com.test.product.inventory.infrastructure.adapter.out.persistence.entity;

import com.test.product.inventory.domain.enums.Status;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product_variants")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductVariantEntity {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column(name = "sku_id", unique = true)
    private String sku;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "price_adjustment", scale = 2, precision = 10, nullable = false)
    private BigDecimal priceAdjustment;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_variant_colors",
            joinColumns = @JoinColumn(name = "product_variant_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private List<ColorEntity> colors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_variant_entity",
            joinColumns = @JoinColumn(name = "product_variant_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<SizeEntity> sizes;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVariantEntity that = (ProductVariantEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
