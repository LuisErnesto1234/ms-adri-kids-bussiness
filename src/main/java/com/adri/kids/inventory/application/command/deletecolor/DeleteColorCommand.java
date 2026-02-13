package com.adri.kids.inventory.application.command.deletecolor;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record DeleteColorCommand(UUID id) implements Command<Void> {
}
