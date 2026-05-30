package com.adri.kids.shared.infrastructure.adapter.out.storage;

import com.adri.kids.shared.domain.port.out.ImageStoragePort;
import com.adri.kids.shared.exceptions.ImageProcessingException;
import com.adri.kids.shared.infrastructure.config.S3Properties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3ImageStorageAdapter implements ImageStoragePort {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    @Override
    public String upload(byte[] imageBytes, String key, String contentType) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(s3Properties.bucket())
                            .key(key)
                            .contentType(contentType)
                            .build(),
                    RequestBody.fromBytes(imageBytes));

            var url = this.buildUrl(key);
            log.info("subiendo imagen a S3: {}", url);

            return url;
        } catch (S3Exception exception) {
            log.error("Error al subir la imagen a S3: {}, Mensaje {}", key, exception.getMessage());
            throw new ImageProcessingException("Fallo al subir la imagen a S3: " + key, exception);
        }
    }

    @Override
    public void delete(String key) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(s3Properties.bucket())
                    .key(key)
                    .build());
        } catch (S3Exception e) {
            // Compensación: si falla no debe ocultar el error original que disparó la limpieza
            log.warn("No se pudo eliminar el objeto S3 durante la compensación: {}", key, e);
        }
    }

    private String buildUrl(String key) {
        return s3Properties.publicBaseUrl() + "/" + key;
    }
}
