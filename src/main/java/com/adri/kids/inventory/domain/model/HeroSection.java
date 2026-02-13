package com.adri.kids.inventory.domain.model;

import com.adri.kids.shared.domain.enums.GeneralStatus;

import java.util.List;
import java.util.UUID;

public record HeroSection(
        UUID id,
        String title,
        String subtitle,
        String ctaText,
        String ctaUrl,
        List<String> imageUrls,
        Integer displayOrder,
        GeneralStatus status
) {

    public HeroSection {
        // Validaciones de Texto
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("El título es obligatorio.");

        // Validaciones de CTA (Lógica de negocio: Si hay URL, debe haber Texto)
        if (ctaUrl != null && !ctaUrl.isBlank() && (ctaText == null || ctaText.isBlank())) {
            throw new IllegalArgumentException("Si hay una URL de destino, el botón debe tener texto.");
        }

        // Validación de Imágenes
        if (imageUrls == null || imageUrls.isEmpty())
            throw new IllegalArgumentException("Debe haber al menos una imagen de fondo.");

        imageUrls = List.copyOf(imageUrls);

        if (displayOrder == null) displayOrder = 0;
    }

    // Factory method actualizado
    public static HeroSection create(String title, String subtitle, String ctaText,
                                     String ctaUrl, List<String> imageUrls, GeneralStatus status) {
        return new HeroSection(
                UUID.randomUUID(),
                title,
                subtitle,
                ctaText,
                ctaUrl,
                imageUrls,
                0,
                 status
        );
    }
}