package com.accenture.cache;

import com.accenture.pojo.Jwt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class RedisCache {

    @Cacheable(value = {"token"}, key = "#token")
    public Jwt autoCacheManager(String token, Jwt jwtInfo) {

        return jwtInfo;
    }

}
