package com.test.product.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;
import com.test.product.inventory.application.querys.getsizes.GetSizesQuery;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.SizeRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.SizeSummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.SizeRestMapper;

import com.test.product.shared.domain.dtos.ApiResponse;
import com.test.product.shared.domain.dtos.PagedResult;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/size")
@RequiredArgsConstructor
public class SizeController {

    private final Pipeline pipeline;
    private final SizeRestMapper sizeRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<SizeSummaryResponse>> createSize(@Valid @RequestBody SizeRequest request) {
        var command = request.toCommand();

        var domain = command.execute(pipeline);

        var response = sizeRestMapper.toResponse(domain);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildCreated(response));
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<ApiResponse<PagedResult<SizeSummaryResponse>>> findAllSize(@PageableDefault Pageable pageable,
                                                                                     @RequestParam(required = false) String search) {
        var query = new GetSizesQuery(pageable, search);

        var response = query.execute(pipeline);

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }
}
