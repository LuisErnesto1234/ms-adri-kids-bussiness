package com.test.product.inventory.application.service;

import com.test.product.inventory.domain.model.Size;
import com.test.product.inventory.domain.port.in.command.size.CreateSizeCommand;
import com.test.product.inventory.domain.port.in.size.CreateSizeUseCase;
import com.test.product.inventory.domain.port.out.SizeRepositoryPort;

public class SizeService implements CreateSizeUseCase {

    private final SizeRepositoryPort repositoryPort;

    public SizeService(SizeRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Size createSize(CreateSizeCommand command) {

        Size size = Size.createSize(command.name(), command.type(), command.sortOrder(), command.status());

        return repositoryPort.save(size);
    }
}
