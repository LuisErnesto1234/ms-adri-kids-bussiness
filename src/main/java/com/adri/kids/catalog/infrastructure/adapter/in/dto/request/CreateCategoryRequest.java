package com.adri.kids.catalog.infrastructure.adapter.in.dto.request;

import com.adri.kids.catalog.application.category.command.CreateCategoryCommand;
import com.adri.kids.shared.domain.dtos.UploadedImage;
import com.adri.kids.shared.exceptions.ImageProcessingException;

import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public record CreateCategoryRequest(
        @NotNull(message = "El nombre de la categoria no puede ser nulo.")
        @NotBlank(message = "El nombre de la categoria no puede estar en blanco.")
        @Size(max = 100, min = 2, message = "El nombre de la categoria debe tener entre 2 y 100 caracteres.")
        String name,
        @NotNull(message = "El slug de la categoria no puede ser nulo.")
        @NotBlank(message = "El slug de la categoria no puede estar en blanco.")
        String slug,
        @Size(min = 50, message = "La descripción corta no puede tener más de 50 caracteres.")
        String descriptionShort,
        @Size(min = 40, max = 500, message = "La descripción larga debe tener entre 40 y 500 caracteres.")
        String descriptionLong,
        @Positive(message = "El número de orden debe ser un valor positivo.")
        int numberOfOrder,
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "El código de color debe ser un valor hexadecimal válido, por ejemplo, #FFFFFF o #FFF.")
        String colorCode,
        boolean isShowMainMenu) {

    private static final Logger log = LoggerFactory.getLogger(CreateCategoryRequest.class);

    /**
     * Convierte el request a un comando de dominio.
     *
     * @param imageFile archivo de imagen opcional
     * @return comando con los datos de la categoría
     * @throws ImageProcessingException si hay error procesando la imagen
     */
    public CreateCategoryCommand toCommand(MultipartFile imageFile) {
        var uploadImage = this.buildUploadImage(imageFile);

        return new CreateCategoryCommand(
                this.name,
                this.slug,
                this.descriptionShort,
                this.descriptionLong,
                uploadImage,
                this.numberOfOrder,
                this.colorCode,
                this.isShowMainMenu);
    }

    /**
     * Construye un objeto UploadedImage desde un MultipartFile.
     *
     * @param imageFile archivo de imagen (puede ser null)
     * @return objeto UploadedImage o null si no se proporcionó archivo
     * @throws ImageProcessingException si hay error leyendo los bytes de la imagen
     */
    private UploadedImage buildUploadImage(MultipartFile imageFile) {
        if (imageFile == null) {
            return null;
        }

        try {
            log.info("Procesando imagen, nombre original: {}", imageFile.getOriginalFilename());
            return new UploadedImage(
                    imageFile.getBytes(),
                    imageFile.getContentType(),
                    imageFile.getOriginalFilename());
        } catch (Exception e) {
            log.error("Error al procesar la imagen: {}", e.getMessage(), e);
            throw new ImageProcessingException("Error al procesar la imagen: " + e.getMessage(), e);
        }
    }
}
