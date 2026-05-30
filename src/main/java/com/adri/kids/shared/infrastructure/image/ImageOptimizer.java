package com.adri.kids.shared.infrastructure.image;

import com.adri.kids.shared.exceptions.ImageProcessingException;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import static com.adri.kids.shared.utils.ConstantUtil.*;

@Slf4j
@Component
public class ImageOptimizer {

    public byte[] toWebp(byte[] imageBytes) {
        try {
            ImmutableImage image = ImmutableImage.loader().fromBytes(imageBytes);
            if (image.width > MAX_WIDTH_UPLOAD_IMG || image.height > MAX_HEIGHT_UPLOAD_IMG) {
                image = image.bound(MAX_WIDTH_UPLOAD_IMG, MAX_HEIGHT_UPLOAD_IMG);
            }

            return image.bytes(WebpWriter.DEFAULT.withQ(WEBP_QUALITY_IMG));
        } catch (Exception e) {
            log.error("Error al optimizar la imagen: {}", e.getMessage(), e);
            throw new ImageProcessingException("No se pudo procesar la imagen.", e);
        }
    }
}
