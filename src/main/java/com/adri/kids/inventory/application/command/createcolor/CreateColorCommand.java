package com.adri.kids.inventory.application.command.createcolor;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.model.Color;

public record CreateColorCommand(String name,
                                 String hexCode,
                                 InventoryStatus status) implements Command<Color> {
}
