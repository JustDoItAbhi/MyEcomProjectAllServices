package cartservice.securityconfigrations;

import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RedisConfig {
    @Bean
    public RestTemplate restTemplate() {// REST TEMPLATE BEAN
        return new RestTemplate();
    }
    public RedisCacheManager manager(RedisConnectionFactory factory){
        RedisCacheConfiguration configuration=RedisCacheConfiguration.defaultCacheConfig();
        GenericJackson2JsonRedisSerializer serializer=new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> serializationPair= RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        configuration=configuration.serializeValuesWith(serializationPair);
        configuration.entryTtl(Duration.ofDays(1));
        return RedisCacheManager.builder(factory).cacheDefaults(configuration).build();
    }

}



