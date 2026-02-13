package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.details.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    Boolean existById(UUID id);

    //Query methods
    Page<Product> findAll(Pageable pageable);

    // El método OPTIMIZADO para listados (Trae la categoría cargada)
    Page<ProductDetails> findAllWithCategory(Pageable pageable);

    List<Product> findAllByCategory(UUID categoryId);

    Optional<ProductDetails> findProductDetailsById(UUID id);
}
