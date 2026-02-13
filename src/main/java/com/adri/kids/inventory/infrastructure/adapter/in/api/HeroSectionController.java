package com.adri.kids.inventory.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.adri.kids.inventory.application.querys.getherosectionbyid.GetHeroSectionByIdQuery;
import com.adri.kids.inventory.application.querys.getherosections.GetHeroSectionsQuery;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.CreateHeroSectionRequest;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.herosection.HeroSectionSummaryResponse;
import com.adri.kids.shared.domain.dtos.ApiResponse;

import com.adri.kids.shared.domain.dtos.PagedResult;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/hero-section")
@RequiredArgsConstructor
public class HeroSectionController {

    private final Pipeline pipeline;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UUID>> createHeroSection(@Valid @RequestBody CreateHeroSectionRequest request) {
        var command = request.toCommand();
        var response = command.execute(pipeline);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildCreated(response));
    }

    @GetMapping("/find-id/{id}")
    public ResponseEntity<ApiResponse<HeroSectionSummaryResponse>> getHeroSectionById(@PathVariable UUID id) {
        var query = new GetHeroSectionByIdQuery(id);

        var response = query.execute(pipeline);

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }

    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse<PagedResult<HeroSectionSummaryResponse>>> getAllHeroSections(@PageableDefault Pageable pageable) {
        var query = new GetHeroSectionsQuery(pageable);

        var response = query.execute(pipeline);

        return ResponseEntity.ok(ApiResponse.buildOk(response));
    }
}
