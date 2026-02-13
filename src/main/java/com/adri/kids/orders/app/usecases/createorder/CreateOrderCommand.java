package com.adri.kids.orders.app.usecases.createorder;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record CreateOrderCommand(String username) implements Command<UUID> {
}
