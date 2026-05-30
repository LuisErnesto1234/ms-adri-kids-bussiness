package com.adri.kids.catalog.application.category.command;

import an.awesome.pipelinr.Command;

import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.shared.domain.dtos.UploadedImage;
import com.adri.kids.shared.domain.port.out.ImageStoragePort;
import com.adri.kids.shared.exceptions.AlreadyExistException;
import com.adri.kids.shared.infrastructure.image.ImageOptimizer;
import com.adri.kids.shared.utils.ConstantUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateCategoryHandler implements Command.Handler<CreateCategoryCommand, Category> {

    private static final String IMAGE_KEY_PREFIX = "categories/";
    private static final String IMAGE_EXTENSION = ".webp";

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final ImageOptimizer imageOptimizer;
    private final ImageStoragePort imageStoragePort;

    @Transactional(
            timeout = ConstantUtil.TIME_OUT_TRANSACTION,
            isolation = Isolation.READ_COMMITTED
    )
    @Override
    public Category handle(CreateCategoryCommand command) {
        validateCategoryDoesNotExist(command.name());

        String imageUrl = processImage(command.image());

        var categoryCreate = Category.createCategory(
                command.name(),
                command.slug(),
                command.descriptionShort(),
                command.descriptionLong(),
                imageUrl,
                command.numberOfOrder(),
                command.colorCode(),
                command.isShowMainMenu());

        return categoryRepositoryPort.save(categoryCreate);
    }

    /**
     * Válida que no exista una categoría con el mismo nombre.
     *
     * @param name el nombre a validar
     * @throws AlreadyExistException si ya existe una categoría con ese nombre
     */
    private void validateCategoryDoesNotExist(String name) {
        if (categoryRepositoryPort.existsByName(name)) {
            throw new AlreadyExistException("El nombre de categoría ya existe: " + name);
        }
    }

    /**
     * Procesa la imagen: la optimiza y la sube al servicio de almacenamiento.
     *
     * @param image la imagen a procesar (puede ser null)
     * @return la URL de la imagen subida, o null si no se proporcionó imagen
     */
    private String processImage(UploadedImage image) {
        return Optional.ofNullable(image)
                .map(this::uploadImage)
                .orElse(null);
    }

    /**
     * Sube la imagen al servicio de almacenamiento con manejo de errores y compensación.
     *
     * @param image la imagen a subir (no nula)
     * @return la URL de la imagen subida
     * @throws RuntimeException sí ocurre un error durante la subida
     */
    private String uploadImage(UploadedImage image) {
        String imageKey = generateImageKey();

        try {
            byte[] optimizedImageBytesWebp = imageOptimizer.toWebp(image.imageBytes());
            return imageStoragePort.upload(optimizedImageBytesWebp, imageKey, ConstantUtil.WEBP_CONTENT_TYPE);
        } catch (RuntimeException exception) {
            handleUploadFailure(imageKey, exception);
            throw exception;
        }
    }

    /**
     * Genera una clave única para la imagen usando UUID.
     *
     * @return la clave compuesta de prefijo, UUID y extensión
     */
    private String generateImageKey() {
        return IMAGE_KEY_PREFIX + UUID.randomUUID() + IMAGE_EXTENSION;
    }

    /**
     * Maneja la falla de subida eliminando el archivo del almacenamiento (compensación).
     *
     * @param imageKey la clave de imagen a limpiar
     * @param originalException la excepción original
     */
    private void handleUploadFailure(String imageKey, RuntimeException originalException) {
        try {
            imageStoragePort.delete(imageKey);
            log.info("Imagen compensada exitosamente: {}", imageKey);
        } catch (RuntimeException cleanupException) {
            log.error("Error al eliminar imagen durante compensación: {}", imageKey, cleanupException);
            originalException.addSuppressed(cleanupException);
        }
    }
}
