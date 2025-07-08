package com.ecom.productservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisCaching {
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration cacheConfiguration=RedisCacheConfiguration.defaultCacheConfig();
        GenericJackson2JsonRedisSerializer serializer=new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> serializationPair= RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        cacheConfiguration=cacheConfiguration.serializeValuesWith(serializationPair);
        cacheConfiguration.entryTtl(Duration.ofDays(1));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }
}
