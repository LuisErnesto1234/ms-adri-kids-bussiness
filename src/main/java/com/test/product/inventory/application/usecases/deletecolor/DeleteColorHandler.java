package com.test.product.inventory.application.usecases.deletecolor;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteColorHandler implements Command.Handler<DeleteColorCommand, Void> {

    private final ColorRepositoryPort colorRepositoryPort;

    @CacheEvict(value = "colors_page", allEntries = true)
    @Transactional(rollbackFor = Exception.class, timeout = 10, propagation = Propagation.REQUIRED)
    @Override
    public Void handle(DeleteColorCommand command) {
        if (colorRepositoryPort.existById(command.id())) {
            colorRepositoryPort.deleteById(command.id());
        }

        throw new NotFoundException("El color no fue encontrado, para su eliminación");
    }
}
