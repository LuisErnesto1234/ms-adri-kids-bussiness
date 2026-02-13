package com.adri.kids.orders.infrastructure.configuration;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.CommandHandlers;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipelineConfiguration {

    // Spring busca automáticamente todos los beans que implementen Command.Handler
    // y nos los entrega en este 'ObjectProvider'
    @Bean
    public Pipeline pipeline(ObjectProvider<Command.Handler> commandHandlers) {
        CommandHandlers handlers = commandHandlers::orderedStream;

        // Creamos una nueva instancia de Pipelinr y le pasamos los handlers encontrados
        return new Pipelinr().with(handlers);
    }
}
