package com.adri.kids.shared.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {

//    @Bean
//    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
//        ObjectMapper mapper = builder.build();
//
//        // ✅ Configuración para que Jackson reconozca Records
//        mapper.acceptJsonFormatVisitor(JavaType.class, );
//
//        return mapper;
//    }

}
