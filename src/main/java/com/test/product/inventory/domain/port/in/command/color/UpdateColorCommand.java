package com.test.product.inventory.domain.port.in.command.color;

import com.test.product.inventory.domain.enums.Status;

import java.util.UUID;

public record UpdateColorCommand(UUID id, String name, String hexCode, Status status) {
}
