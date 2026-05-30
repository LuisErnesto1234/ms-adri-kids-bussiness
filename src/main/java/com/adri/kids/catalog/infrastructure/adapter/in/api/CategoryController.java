package com.adri.kids.catalog.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;

import com.adri.kids.catalog.application.category.command.ActivateCategoryCommand;
import com.adri.kids.catalog.application.category.command.DeleteCategoryCommand;
import com.adri.kids.catalog.application.category.query.GetCategoriesQuery;
import com.adri.kids.catalog.application.category.query.GetCategoryByIdQuery;
import com.adri.kids.catalog.application.category.command.DeactivateCategoryCommand;
import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.request.CreateCategoryRequest;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.request.UpdateCategoryRequest;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.request.filter.CategoryFilterRequest;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.response.CategoryCardResponse;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.response.CategoryDetailResponse;
import com.adri.kids.catalog.infrastructure.adapter.in.mapper.CategoryRestMapper;

import com.adri.kids.shared.domain.dtos.ApiResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final Pipeline pipeline;
    private final CategoryRestMapper categoryRestMapper;

    /**
     * Crea una nueva categoría con imagen opcional.
     *
     * @param createRequest datos de la categoría a crear (validado)
     * @param imageFile     archivo de imagen opcional
     * @return respuesta con la categoría creada
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CategoryCardResponse>> create(
            @Valid @RequestPart("data") CreateCategoryRequest createRequest,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        var command = createRequest.toCommand(imageFile);
        var createdCategory = executeCommand(command);
        var responseDto = categoryRestMapper.toResponse(createdCategory);

        log.info("Categoría creada exitosamente: {}, nombre: {}", createdCategory.id(), createdCategory.name());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildCreated(responseDto));
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param categoryId    identificador de la categoría
     * @param updateRequest datos actualizados
     * @return respuesta con la categoría actualizada
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<CategoryCardResponse>> update(
            @PathVariable(name = "id") UUID categoryId,
            @Valid @RequestBody UpdateCategoryRequest updateRequest) {

        var updateCategoryCommand = updateRequest.toCommand(categoryId);
        var updatedCategory = executeCommand(updateCategoryCommand);
        var responseDto = categoryRestMapper.toResponse(updatedCategory);

        log.info("Categoría actualizada: {}", categoryId);
        return ResponseEntity.ok(ApiResponse.buildOk(responseDto));
    }

    /**
     * Activa una categoría por su ID.
     *
     * @param categoryId identificador de la categoría
     * @return respuesta con la categoría activada
     */
    @PatchMapping(path = "/{id}/activate")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> activate(@PathVariable(name = "id") UUID categoryId) {
        var command = new ActivateCategoryCommand(categoryId);
        var activatedCategory = executeCommand(command);
        var responseDto = categoryRestMapper.toResponseDetail(activatedCategory);

        log.info("Categoría activada: {}", categoryId);
        return ResponseEntity.ok(ApiResponse.buildOk(responseDto));
    }

    /**
     * Desactiva una categoría por su ID.
     *
     * @param categoryId identificador de la categoría
     * @return respuesta con la categoría desactivada
     */
    @PatchMapping(path = "/{id}/deactivate")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> deactivate(@PathVariable(name = "id") UUID categoryId) {
        var command = new DeactivateCategoryCommand(categoryId);
        var deactivatedCategory = executeCommand(command);
        var responseDto = categoryRestMapper.toResponseDetail(deactivatedCategory);

        log.info("Categoría desactivada: {}", categoryId);
        return ResponseEntity.ok(ApiResponse.buildOk(responseDto));
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param categoryId identificador de la categoría
     * @return respuesta vacía sin contenido
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable(name = "id") UUID categoryId) {
        var deleteCommand = new DeleteCategoryCommand(categoryId);
        executeCommand(deleteCommand);

        log.info("Categoría eliminada: {}", categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.buildNoContent("Categoría eliminada exitosamente"));
    }

    /**
     * Obtiene todas las categorías con paginación y filtros.
     *
     * @param pageable      información de paginación (página y tamaño)
     * @param filterRequest filtros opcionales
     * @return respuesta con lista paginada de categorías
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResult<CategoryCardResponse>>> getAll(
            @PageableDefault Pageable pageable,
            @ModelAttribute CategoryFilterRequest filterRequest) {

        var query = new GetCategoriesQuery(pageable.getPageNumber(), pageable.getPageSize(), filterRequest);
        var pagedCategories = executeCommand(query);
        var responseDtos = mapCategoryListToResponse(pagedCategories);
        var pagedResponse = buildPagedResult(pagedCategories, responseDtos);

        log.info("Se obtuvieron {} categorías", pagedCategories.getContent().size());
        return ResponseEntity.ok(ApiResponse.buildOk(pagedResponse));
    }

    /**
     * Obtiene una categoría específica por su ID.
     *
     * @param categoryId identificador de la categoría
     * @return respuesta con la categoría solicitada
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> getById(@PathVariable(name = "id") UUID categoryId) {
        var query = new GetCategoryByIdQuery(categoryId);
        var category = executeCommand(query);
        var categoryResponseDto = categoryRestMapper.toResponseDetail(category);

        return ResponseEntity.ok(ApiResponse.buildOk(categoryResponseDto));
    }

    /**
     * Ejecuta un comando en el pipeline y retorna el resultado.
     *
     * @param command el comando a ejecutar
     * @param <R>     tipo del resultado
     * @return resultado del comando
     */
    private <R> R executeCommand(Command<R> command) {
        return command.execute(pipeline);
    }

    /**
     * Mapea una lista de categorías a sus DTOs de respuesta.
     *
     * @param pagedCategories página de categorías del dominio
     * @return lista de DTOs mapeados
     */
    private List<CategoryCardResponse> mapCategoryListToResponse(
            PagedResult<Category> pagedCategories) {
        return pagedCategories.getContent().stream()
                .filter(Objects::nonNull)
                .map(categoryRestMapper::toResponse)
                .toList();
    }

    /**
     * Construye una respuesta paginada convirtiendo categorías a DTOs.
     *
     * @param pagedCategories datos paginados del dominio
     * @param responseDtos    DTOs ya mapeados
     * @return resultado paginado para la respuesta HTTP
     */
    private PagedResult<CategoryCardResponse> buildPagedResult(
            PagedResult<Category> pagedCategories,
            List<CategoryCardResponse> responseDtos) {
        return new PagedResult<>(
                responseDtos,
                pagedCategories.getPage(),
                pagedCategories.getSize(),
                pagedCategories.getTotalElements(),
                pagedCategories.getTotalPages());
    }
}
