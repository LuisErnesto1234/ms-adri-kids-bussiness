package com.adri.kids.catalog.application.color.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.shared.domain.enums.InventoryStatus;
import com.adri.kids.catalog.domain.model.Color;

import java.util.UUID;

public record UpdateColorCommand(UUID id, String name,
                                 String hexCode, InventoryStatus status) implements Command<Color> {
}
