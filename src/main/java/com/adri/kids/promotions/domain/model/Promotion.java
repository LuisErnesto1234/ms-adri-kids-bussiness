package com.adri.kids.promotions.domain.model;

import com.adri.kids.promotions.domain.enums.Status;

import java.time.Instant;
import java.util.UUID;

public record Promotion(UUID id, String name, String code, String description,
                        Double percentDiscount,
                        Boolean isMultipleUses, Integer usesCount,
                        Instant expirationDate,
                        Instant createdAt, Instant updatedAt, Status status) {
}
