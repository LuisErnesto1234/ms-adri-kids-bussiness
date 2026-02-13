package com.adri.kids.inventory.application.command.createcategory;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.shared.domain.enums.GeneralStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateCategoryHandlerTest {

    @Mock
    private CategoryRepositoryPort repositoryPort;

    @InjectMocks
    private CreateCategoryHandler createCategoryHandler;

    @Test
    void handle_happyCase() {
        // ACT
        CreateCategoryCommand command = new CreateCategoryCommand("Electronics",
                "Category for electronic devices", "http://image.png", GeneralStatus.ACTIVE);
        Category categoryExpected = new Category(UUID.randomUUID(), command.name(), command.description(), command.urlImage(), Instant.now(),
                Instant.now(), command.status());

        Mockito.when(repositoryPort.save(Mockito.any(Category.class)))
                .thenReturn(categoryExpected);

        Category categoryActual = createCategoryHandler.handle(command);

        assertNotNull(categoryActual);
        assertEquals(categoryExpected, categoryActual);
        assertEquals(categoryExpected.name(), categoryActual.name());
    }

    @Test
    void handle_errorCase(){
        // ARRANGE
        CreateCategoryCommand command = new CreateCategoryCommand("Electronics",
                "Category for electronic devices", "http://image.png", GeneralStatus.ACTIVE);
        Mockito.when(repositoryPort.save(Mockito.any(Category.class)))
                .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> createCategoryHandler.handle(command));
        assertEquals("Database error", exception.getMessage());

        Mockito.verify(repositoryPort, Mockito.times(1)).save(Mockito.any(Category.class));
    }
}