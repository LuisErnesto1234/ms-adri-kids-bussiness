package com.adri.kids.shared.domain.dtos;

import java.util.Arrays;

/**
 * Value Object que representa los datos de la imagen a persistir.
 *
 * @param imageBytes       Los bytes de la imagen cargada.
 * @param contentType      El tipo de contenido de la imagen.
 * @param originalFilename El nombre original de la imagen
 */
public record UploadedImage(
        byte[] imageBytes,
        String contentType,
        String originalFilename) {

    public UploadedImage {
        if (imageBytes == null || imageBytes.length == 0) {
            throw new IllegalArgumentException("La imagen no puede estar vacía.");
        }
        if (contentType == null || contentType.isBlank()) {
            throw new IllegalArgumentException("El tipo de contenido es obligatorio.");
        }
        if (!contentType.startsWith("image/")) {
            throw new IllegalArgumentException("El archivo debe ser una imagen.");
        }

        imageBytes = Arrays.copyOf(imageBytes, imageBytes.length);
    }

    public byte[] imageBytes() {
        return Arrays.copyOf(imageBytes, imageBytes.length);
    }
}
