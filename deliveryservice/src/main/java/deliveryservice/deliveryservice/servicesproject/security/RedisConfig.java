package deliveryservice.deliveryservice.servicesproject.security;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import deliveryservice.deliveryservice.servicesproject.orderservice.dtos.CheckOutOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, CheckOutOrder> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, CheckOutOrder> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        Jackson2JsonRedisSerializer<CheckOutOrder> serializer = new Jackson2JsonRedisSerializer<>(mapper, CheckOutOrder.class);

//        template.setDefaultSerializer(serializer);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(serializer);
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(serializer);
        template.setValueSerializer(serializer);
        return template;
    }
}
