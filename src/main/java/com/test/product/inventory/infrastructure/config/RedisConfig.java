package com.test.product.inventory.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;

import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;
import tools.jackson.databind.module.SimpleModule;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    //Json vitaminado
    @Bean
    public GenericJacksonJsonRedisSerializer jsonSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();

        PolymorphicTypeValidator typeValidator = new DefaultBaseTypeLimitingValidator();
        JacksonModule module = new SimpleModule();

        objectMapper.rebuild()
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .activateDefaultTyping(typeValidator, DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
                .addModule(module)
                .build();

        return new GenericJacksonJsonRedisSerializer(objectMapper);
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
                                          GenericJacksonJsonRedisSerializer jsonSerializer){

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10L))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration("products_page", defaultConfig.entryTtl(Duration.ofMinutes(30L)))
                .withCacheConfiguration("categories_page", defaultConfig.entryTtl(Duration.ofMinutes(30L)))
                .build();


    }

}
