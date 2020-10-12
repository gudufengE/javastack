package com.javastack.productservice.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisLettuceConfig {

    //    RedisAutoConfiguration类型下的RedisTemplate的序列化是默认的，不友好，所以这里重新声明
    //    至于StringRedisTemplate就没有必要了，使用它默认的序列化就好了，看下源码就明白了
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());//有些地方也用：keySerializer()
        redisTemplate.setValueSerializer(valueSerializer());

        return redisTemplate;
    }

    private RedisSerializer<String> keySerializer() {
        return RedisSerializer.string();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

}