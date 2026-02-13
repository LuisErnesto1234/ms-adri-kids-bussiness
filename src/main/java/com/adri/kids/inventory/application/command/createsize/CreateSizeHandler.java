package com.adri.kids.inventory.application.command.createsize;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.model.Size;
import com.adri.kids.inventory.domain.port.out.SizeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateSizeHandler implements Command.Handler<CreateSizeCommand, Size> {

    private final SizeRepositoryPort repositoryPort;

    @Transactional(rollbackFor = Exception.class, timeout = 15, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @CacheEvict(value = "size_page", allEntries = true)
    @Override
    public Size handle(CreateSizeCommand command) {
        Size size = Size.createSize(command.name(), command.type(),
                command.sortOrder(), command.status());

        return repositoryPort.save(size);
    }
}
