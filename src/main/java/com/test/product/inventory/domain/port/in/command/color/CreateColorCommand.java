package com.test.product.inventory.domain.port.in.command.color;

import com.test.product.inventory.domain.enums.Status;

public record CreateColorCommand(String name, String hexCode, Status status) {
}
