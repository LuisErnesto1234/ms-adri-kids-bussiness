package com.adri.kids.inventory.domain.port.out;

import com.adri.kids.inventory.domain.model.ProductVariant;
import com.adri.kids.inventory.domain.model.details.ProductVariantDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductVariantRepositoryPort {
    ProductVariant save(ProductVariant productVariant);

    Optional<ProductVariant> findById(UUID id);

    Boolean existById(UUID id);

    Page<ProductVariant> findAll(Pageable pageable);

    Optional<ProductVariantDetails> findProductVariantDetailById(UUID id);

}
