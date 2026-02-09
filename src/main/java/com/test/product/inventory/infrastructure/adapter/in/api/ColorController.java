package com.test.product.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.test.product.inventory.application.querys.getcolors.GetColorsQuery;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.ColorRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ColorSummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ColorRestMapper;
import com.test.product.shared.domain.ApiResponse;
import com.test.product.shared.domain.PagedResult;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/color")
@RequiredArgsConstructor
public class ColorController {

    private final Pipeline pipeline;
    private final ColorRestMapper colorRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<ColorSummaryResponse>> createColor(@Valid @RequestBody ColorRequest request) {
        var command = request.toCommand();

        var domain = command.execute(pipeline);

        var response = colorRestMapper.toResponse(domain);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildCreated(response));
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<ApiResponse<PagedResult<ColorSummaryResponse>>> findAllColors(@PageableDefault Pageable pageable,
                                                                                        @RequestParam String search) {
        var command = new GetColorsQuery(pageable, search);

        var response = command.execute(pipeline);

        return ResponseEntity.ok(ApiResponse.buildOk(response));


    }
}
