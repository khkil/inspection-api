package com.example.backend.api.auth.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    public void setValues(String key, String value){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    public void setValuesExpire(String key, String value, long duration){
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public Object getValues(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }


}
