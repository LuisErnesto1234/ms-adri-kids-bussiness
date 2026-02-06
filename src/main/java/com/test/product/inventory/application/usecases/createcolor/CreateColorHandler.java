package com.test.product.inventory.application.usecases.createcolor;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateColorHandler implements Command.Handler<CreateColorCommand, Color> {

    private final ColorRepositoryPort colorRepositoryPort;

    @Transactional
    @Override
    public Color handle(CreateColorCommand command) {
        Color color = Color.createColor(command.name(), command.hexCode(), command.status());
        return colorRepositoryPort.save(color);
    }
}
