package com.adri.kids.inventory.application.command.createproductvariant;

import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.enums.TypeProduct;
import com.adri.kids.inventory.domain.model.Color;
import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.inventory.domain.model.ProductVariant;
import com.adri.kids.inventory.domain.model.Size;
import com.adri.kids.inventory.domain.model.details.ProductVariantDetails;
import com.adri.kids.inventory.domain.port.out.ColorRepositoryPort;
import com.adri.kids.inventory.domain.port.out.ProductRepositoryPort;
import com.adri.kids.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.adri.kids.inventory.domain.port.out.SizeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductVariantHandlerTest {

    @Mock
    private ProductVariantRepositoryPort productVariantRepositoryPort;
    @Mock
    private ProductRepositoryPort productRepositoryPort;
    @Mock
    private ColorRepositoryPort colorRepositoryPort;
    @Mock
    private SizeRepositoryPort sizeRepositoryPort;
    @Mock
    private Clock  clock;

    @InjectMocks
    private CreateProductVariantHandler createProductVariantHandler;

    @BeforeEach
    void setUp() {
        // Creamos un tiempo fijo: 2026-02-11 10:00:00
        Instant fixedInstant = Instant.parse("2026-02-11T10:00:00Z");
        clock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));

        // Inicializamos el handler con el reloj fijo
        createProductVariantHandler = new CreateProductVariantHandler(
                productVariantRepositoryPort,
                productRepositoryPort,
                colorRepositoryPort,
                sizeRepositoryPort,
                clock
        );
    }

    @Test
    void handle_happyCase() {
        UUID productId = UUID.randomUUID();
        UUID colorId = UUID.randomUUID();
        UUID sizeId = UUID.randomUUID();

        var command = new CreateProductVariantCommand(productId, colorId,
                sizeId, "sku-test", 10, BigDecimal.valueOf(10.00),
                "http://imagen.png", InventoryStatus.AVAILABLE);

        var productVariant = new ProductVariant(UUID.randomUUID(), command.productId(), command.colorId(),
                command.sizeId(), command.sku(), command.stockQuantity(), command.priceAdjustment(),
                command.imageUrl(), Instant.now(), Instant.now(), command.status());

        var productMock = new Product(productId, UUID.randomUUID(), "name", "description",
                BigDecimal.valueOf(2.5), "", List.of(), 1,
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        var colorMock = new Color(colorId, "name", "description",
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        var sizeMock = new Size(sizeId, "M", TypeProduct.T_SHIRT, "10",
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        when(productRepositoryPort.findById(productId))
                .thenReturn(Optional.of(productMock));

        when(colorRepositoryPort.findById(colorId))
                .thenReturn(Optional.of(colorMock));

        when(sizeRepositoryPort.findById(sizeId))
                .thenReturn(Optional.of(sizeMock));

        when(productVariantRepositoryPort.save(ArgumentMatchers.any(ProductVariant.class)))
                .thenReturn(productVariant);

        var resultExpected = new ProductVariantDetails(productVariant.id(), productMock, colorMock, sizeMock,
                productVariant.sku(), productVariant.stockQuantity(), productVariant.priceAdjustment(),
                productVariant.imageUrl(), productVariant.createdAt(), productVariant.updatedAt(), productVariant.status());

        var result = createProductVariantHandler.handle(command);

        assertNotNull(result);
        assertEquals(resultExpected, result);

        verify(productVariantRepositoryPort, Mockito.times(1))
                .save(ArgumentMatchers.any(ProductVariant.class));
    }

    @Test
    void handle_shouldThrowException_whenSkuAlreadyExists() {
        // 1. Preparación de datos
        UUID productId = UUID.randomUUID();
        UUID colorId = UUID.randomUUID();
        UUID sizeId = UUID.randomUUID();
        String duplicatedSku = "sku-existente";

        var command = new CreateProductVariantCommand(productId, colorId,
                sizeId, duplicatedSku, 10, BigDecimal.valueOf(10.00),
                "http://imagen.png", InventoryStatus.AVAILABLE);

        // Creamos una variante que ya existe para meterla en el producto
        var existingVariant = new ProductVariant(UUID.randomUUID(), productId, colorId,
                sizeId, duplicatedSku, 5, BigDecimal.valueOf(10.00),
                "img.png", Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        // El producto ya viene con la variante existente
        var productWithVariant = new Product(productId, UUID.randomUUID(),
                "Laptop", "Desc", BigDecimal.valueOf(1000), "",
                List.of(existingVariant), 1, // <--- Aquí ya está el SKU
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        // 2. Configuración de Mocks
        when(productRepositoryPort.findById(productId)).thenReturn(Optional.of(productWithVariant));
        when(colorRepositoryPort.findById(colorId)).thenReturn(Optional.of(new Color(colorId, "name", "description",
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE)));
        when(sizeRepositoryPort.findById(sizeId)).thenReturn(Optional.of(new Size(sizeId, "M", TypeProduct.T_SHIRT, "10",
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createProductVariantHandler.handle(command));

        assertEquals("La variante con ID o SKU ya existe en este producto.", exception.getMessage());

        verify(productVariantRepositoryPort, never()).save(any());
    }

    @Test
    void handle_shouldSkuIsNotPresent() {
        UUID productId = UUID.randomUUID();
        UUID colorId = UUID.randomUUID();
        UUID sizeId = UUID.randomUUID();

        var command = new CreateProductVariantCommand(productId, colorId,
                sizeId, null, 10, BigDecimal.valueOf(10.00),
                "http://imagen.png", InventoryStatus.AVAILABLE);

        var productMock = new Product(productId, UUID.randomUUID(), "name", "description",
                BigDecimal.valueOf(2.5), "", List.of(), 1,
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        var colorMock = new Color(colorId, "name", "description",
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        var sizeMock = new Size(sizeId, "M", TypeProduct.T_SHIRT, "10",
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        when(productRepositoryPort.findById(productId))
                .thenReturn(Optional.of(productMock));

        when(colorRepositoryPort.findById(colorId))
                .thenReturn(Optional.of(colorMock));

        when(sizeRepositoryPort.findById(sizeId))
                .thenReturn(Optional.of(sizeMock));

        when(productVariantRepositoryPort.save(ArgumentMatchers.any(ProductVariant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = createProductVariantHandler.handle(command);

        assertNotNull(result.sku());

        String expectedSku = "name-name-1770804000";

        assertEquals(expectedSku, result.sku());
        assertEquals(productMock.name(), result.product().name());
        assertEquals(colorMock.name(), result.color().name());

        verify(productVariantRepositoryPort, Mockito.times(1))
                .save(ArgumentMatchers.any(ProductVariant.class));
    }

    @Test
    void handle_shouldSkuIsBlank() {
        UUID productId = UUID.randomUUID();
        UUID colorId = UUID.randomUUID();
        UUID sizeId = UUID.randomUUID();

        var command = new CreateProductVariantCommand(productId, colorId,
                sizeId, "", 10, BigDecimal.valueOf(10.00),
                "http://imagen.png", InventoryStatus.AVAILABLE);

        var productMock = new Product(productId, UUID.randomUUID(), "name", "description",
                BigDecimal.valueOf(2.5), "", List.of(), 1,
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        var colorMock = new Color(colorId, "name", "description",
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        var sizeMock = new Size(sizeId, "M", TypeProduct.T_SHIRT, "10",
                Instant.now(), Instant.now(), InventoryStatus.AVAILABLE);

        when(productRepositoryPort.findById(productId))
                .thenReturn(Optional.of(productMock));

        when(colorRepositoryPort.findById(colorId))
                .thenReturn(Optional.of(colorMock));

        when(sizeRepositoryPort.findById(sizeId))
                .thenReturn(Optional.of(sizeMock));

        when(productVariantRepositoryPort.save(ArgumentMatchers.any(ProductVariant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = createProductVariantHandler.handle(command);

        assertNotNull(result.sku());

        String expectedSku = "name-name-1770804000";

        assertEquals(expectedSku, result.sku());
        assertEquals(productMock.name(), result.product().name());
        assertEquals(colorMock.name(), result.color().name());

        verify(productVariantRepositoryPort, Mockito.times(1))
                .save(ArgumentMatchers.any(ProductVariant.class));
    }
}