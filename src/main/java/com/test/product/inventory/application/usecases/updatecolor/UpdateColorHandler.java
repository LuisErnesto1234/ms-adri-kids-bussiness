package com.test.product.inventory.application.usecases.updatecolor;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateColorHandler implements Command.Handler<UpdateColorCommand, Color> {

    private final ColorRepositoryPort colorRepositoryPort;

    @Transactional(timeout = 10, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "color", key = "'color:' + #command.id", condition = "#result != null"),
            @CacheEvict(value = "color_page",  allEntries = true, condition = "#result != null")
    })
    @Override
    public Color handle(UpdateColorCommand command) {
        Color colorFind = colorRepositoryPort.findById(command.id())
                .orElseThrow(() -> new NotFoundException("El color no fue encontrado, para la actualizacion"));

        Color colorUpdate = Color.updateColor(colorFind.id(), command.name(),
                command.hexCode(), colorFind.createdAt(), command.status());

        return colorRepositoryPort.save(colorUpdate);
    }
}
