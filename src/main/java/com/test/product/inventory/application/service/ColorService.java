package com.test.product.inventory.application.service;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.port.in.color_use_case.CreateColorUseCase;
import com.test.product.inventory.domain.port.in.color_use_case.DeleteColorUseCase;
import com.test.product.inventory.domain.port.in.color_use_case.UpdateColorUseCase;
import com.test.product.inventory.domain.port.in.command.color.CreateColorCommand;
import com.test.product.inventory.domain.port.in.command.color.DeleteColorCommand;
import com.test.product.inventory.domain.port.in.command.color.UpdateColorCommand;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;

public class ColorService implements CreateColorUseCase, UpdateColorUseCase, DeleteColorUseCase {

    private final ColorRepositoryPort colorRepositoryPort;

    public ColorService(ColorRepositoryPort colorRepositoryPort) {
        this.colorRepositoryPort = colorRepositoryPort;
    }

    @Override
    public Color createColor(CreateColorCommand command) {
        Color color = Color.createColor(command.name(), command.hexCode(), command.status());
        return colorRepositoryPort.save(color);
    }

    @Override
    public Color updateColor(UpdateColorCommand command) {
        Color colorFind = colorRepositoryPort.findById(command.id())
                .orElseThrow(() -> new NotFoundException("El color no fue encontrado"));
        Color colorUpdate = Color.updateColor(colorFind.id(), command.name(), command.hexCode(), colorFind.createdAt(), command.status());
        return colorRepositoryPort.save(colorUpdate);
    }

    @Override
    public void deleteColor(DeleteColorCommand command) {
        if (colorRepositoryPort.existById(command.id())) {
            colorRepositoryPort.deleteById(command.id());
        }

        throw new NotFoundException("El color no fue encontrado");
    }
}
