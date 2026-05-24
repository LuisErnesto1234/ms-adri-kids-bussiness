package com.adri.kids.catalog.application.color.command;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record DeleteColorCommand(UUID id) implements Command<Void> {
}
