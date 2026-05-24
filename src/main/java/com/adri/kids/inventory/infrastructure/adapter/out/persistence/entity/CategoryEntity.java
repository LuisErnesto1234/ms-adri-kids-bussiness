package com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity;

import com.adri.kids.shared.domain.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "color_code_of_theme", nullable = false)
    private ColorEntity colorCodeOfTheme;

    @Column(name = "is_publish_immediately")
    private Boolean isPublishImmediately;

    @Column(name = "is_show_main_menu")
    private Boolean isShowMainMenu;

    @Column(name = "image_url", length = 550)
    private String urlImage;

    @Column(name = "slug", length = 100, unique = true, nullable = false)
    private String slug;

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
    private GeneralStatus status;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        CategoryEntity that = (CategoryEntity) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(descriptionShort, that.descriptionShort) && Objects.equals(descriptionLong, that.descriptionLong) && Objects.equals(numberOfOrder, that.numberOfOrder) && Objects.equals(colorCodeOfTheme, that.colorCodeOfTheme) && Objects.equals(isPublishImmediately, that.isPublishImmediately) && Objects.equals(isShowMainMenu, that.isShowMainMenu) && Objects.equals(urlImage, that.urlImage) && Objects.equals(slug, that.slug) && Objects.equals(productEntities, that.productEntities) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, descriptionShort, descriptionLong, numberOfOrder, colorCodeOfTheme, isPublishImmediately, isShowMainMenu, urlImage, slug, productEntities, createdAt, updatedAt, status);
    }
}
