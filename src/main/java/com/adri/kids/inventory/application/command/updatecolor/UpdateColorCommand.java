package com.adri.kids.inventory.application.command.updatecolor;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.model.Color;

import java.util.UUID;

public record UpdateColorCommand(UUID id, String name,
                                 String hexCode, InventoryStatus status) implements Command<Color> {
}
