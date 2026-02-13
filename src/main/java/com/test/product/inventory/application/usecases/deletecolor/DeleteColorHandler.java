package com.test.product.inventory.application.usecases.deletecolor;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteColorHandler implements Command.Handler<DeleteColorCommand, Void> {

    private final ColorRepositoryPort colorRepositoryPort;

    @Transactional(timeout = 10, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "color_page", allEntries = true),
            @CacheEvict(value = "color", key = "'color:' + #command.id")
    })
    @Override
    public Void handle(DeleteColorCommand command) {
        if (Boolean.TRUE.equals(colorRepositoryPort.existById(command.id()))) {
            colorRepositoryPort.deleteById(command.id());
        }

        throw new NotFoundException("El color no fue encontrado, para su eliminación");
    }
}
