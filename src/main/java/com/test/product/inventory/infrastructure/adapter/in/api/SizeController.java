package com.test.product.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.SizeRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.SizeResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.SizeRestMapper;
import com.test.product.inventory.infrastructure.utils.ApiResponse;
import com.test.product.inventory.infrastructure.utils.ConstantUtil;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(value = "/api/v1/size")
@RequiredArgsConstructor
public class SizeController {

    private final Pipeline pipeline;
    private final SizeRestMapper sizeRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<SizeResponse>> createSize(@Valid @RequestBody SizeRequest request){
        var command = request.toCommand();

        var domain = command.execute(pipeline);

        var response = sizeRestMapper.toResponse(domain);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<SizeResponse>builder()
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .data(response)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

}
