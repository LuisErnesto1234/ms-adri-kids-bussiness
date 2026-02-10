package com.test.product.shared.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import tools.jackson.databind.*;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        config.setPassword(redisPassword);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public RedisSerializer<Object> genericJacksonJsonRedisSerializer() {
        BasicPolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.test.product")
                .allowIfSubType("java.util")
                .allowIfSubType("java.lang")
                .allowIfSubType("java.math")
                .allowIfSubType("java.time")
                .allowIfSubType("org.springframework.data.domain")
                .build();

        return GenericJacksonJsonRedisSerializer.builder()
                .typeValidator(typeValidator)
                .typePropertyName("@class")
                .customize(builder -> {
                    // Módulos necesarios
//                    builder.addModule();

                    // Configuraciones básicas
                    builder.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//                    builder.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    builder.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);

                    // ✅ CAMBIO CRÍTICO: De OBJECT_AND_NON_CONCRETE a NON_FINAL
                    builder.activateDefaultTyping(
                            typeValidator,
                            DefaultTyping.NON_FINAL, // ⬅️ Esto resuelve el problema de listas
                            JsonTypeInfo.As.PROPERTY
                    );
                })
                .build();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10L))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJacksonJsonRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration("products_page", defaultConfig.entryTtl(Duration.ofMinutes(30L)))
                .withCacheConfiguration("categories_page", defaultConfig.entryTtl(Duration.ofMinutes(30L)))
                .withCacheConfiguration("colors_page", defaultConfig.entryTtl(Duration.ofMinutes(30L)))
                .build();
    }
}
