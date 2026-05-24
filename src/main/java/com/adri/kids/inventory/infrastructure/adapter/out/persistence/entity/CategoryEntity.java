package com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity;

import com.adri.kids.shared.domain.enums.GeneralStatus;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @Id
    private UUID id;

    @Column(name = "name", length = 200, unique = true)
    private String name;

    @Column(length = 100, nullable = false)
    private String descriptionShort;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String descriptionLong;

    @Column(length = 100, nullable = false, name = "number_of_order")
    private Integer numberOfOrder;

    @Column(name = "is_show_main_menu")
    private boolean isShowMainMenu;

    @Column(name = "image_url", length = 550)
    private String urlImage;

    @Column(name = "slug", length = 100, unique = true, nullable = false)
    private String slug;

    @Column(length = 20, name = "color_code")
    private String colorCode;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductEntity> productEntities;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return isShowMainMenu == that.isShowMainMenu
                && Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(descriptionShort, that.descriptionShort)
                && Objects.equals(descriptionLong, that.descriptionLong)
                && Objects.equals(numberOfOrder, that.numberOfOrder)
                && Objects.equals(urlImage, that.urlImage)
                && Objects.equals(slug, that.slug)
                && Objects.equals(colorCode, that.colorCode)
                && Objects.equals(productEntities, that.productEntities)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name,
                descriptionShort,
                descriptionLong,
                numberOfOrder,
                isShowMainMenu,
                urlImage, slug,
                colorCode,
                productEntities,
                createdAt,
                updatedAt,
                status);
    }
}
