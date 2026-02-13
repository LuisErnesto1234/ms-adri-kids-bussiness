package com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity;

import com.adri.kids.shared.domain.enums.GeneralStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hero_section")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroSectionEntity {
    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "sub_title", nullable = false)
    private String subtitle;
    @Column(name = "cta_text")
    private String ctaText;
    @Column(name = "cta_url")
    private String ctaUrl;
    @Column(name = "image_urls")
    private List<String> imageUrls;
    @Column(name = "display_order")
    private Integer displayOrder;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
