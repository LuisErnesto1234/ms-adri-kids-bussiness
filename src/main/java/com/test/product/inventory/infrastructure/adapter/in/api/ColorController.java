package com.test.product.inventory.infrastructure.adapter.in.api;

import com.test.product.inventory.domain.port.in.color_use_case.CreateColorUseCase;
import com.test.product.inventory.infrastructure.adapter.in.dto.request.ColorRequest;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ColorResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ColorRestMapper;
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
@RequestMapping(value = "/api/v1/color")
@RequiredArgsConstructor
public class ColorController {

    private final CreateColorUseCase createColorUseCase;
    private final ColorRestMapper colorRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<ColorResponse>> createColor(@Valid @RequestBody ColorRequest request){
        var command = colorRestMapper.toCommand(request);

        var domain = createColorUseCase.createColor(command);

        var response = colorRestMapper.toResponse(domain);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<ColorResponse>builder()
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .data(response)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

}
