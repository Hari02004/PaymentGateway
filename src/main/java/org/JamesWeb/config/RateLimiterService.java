package org.JamesWeb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static final int LIMIT = 5;

    public boolean allowRequest(String apiKey){

        String key = "rate_limit:" + apiKey;

        Long count =
                redisTemplate.opsForValue().increment(key);

        if(count == 1){

            redisTemplate.expire(
                    key,
                    Duration.ofMinutes(1));
        }

        return count <= LIMIT;
    }
}