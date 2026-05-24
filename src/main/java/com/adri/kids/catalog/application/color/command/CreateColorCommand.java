package com.adri.kids.catalog.application.color.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.shared.domain.enums.InventoryStatus;
import com.adri.kids.catalog.domain.model.Color;

public record CreateColorCommand(String name,
                                 String hexCode,
                                 InventoryStatus status) implements Command<Color> {
}
