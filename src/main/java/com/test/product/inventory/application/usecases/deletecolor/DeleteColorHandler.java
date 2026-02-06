package com.test.product.inventory.application.usecases.deletecolor;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteColorHandler implements Command.Handler<DeleteColorCommand, Void> {

    private final ColorRepositoryPort colorRepositoryPort;

    @Override
    public Void handle(DeleteColorCommand command) {
        if (colorRepositoryPort.existById(command.id())) {
            colorRepositoryPort.deleteById(command.id());
        }

        throw new NotFoundException("El color no fue encontrado, para su eliminación");
    }
}
