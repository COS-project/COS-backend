package com.cos.cercat.common.config;

import com.cos.cercat.search.cache.SearchLog;
import com.cos.cercat.user.cache.RefreshToken;
import com.cos.cercat.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;

@EnableRedisRepositories
@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, SseEmitter> sseEmitterRedisTemplate() {
        RedisTemplate<String, SseEmitter> sseEmitterRedisTemplate = new RedisTemplate<>();
        sseEmitterRedisTemplate.setConnectionFactory(redisConnectionFactory());
        sseEmitterRedisTemplate.setKeySerializer(new StringRedisSerializer());
        sseEmitterRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SseEmitter.class));
        return sseEmitterRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, UserDTO> userRedisTemplate() {
        RedisTemplate<String, UserDTO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserDTO.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, RefreshToken> tokenRedisTemplate() {
        RedisTemplate<String, RefreshToken> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RefreshToken.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, String> logoutRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, SearchLog> searchLogRedisTemplate() {
        RedisTemplate<String, SearchLog> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SearchLog.class));
        return redisTemplate;
    }

}