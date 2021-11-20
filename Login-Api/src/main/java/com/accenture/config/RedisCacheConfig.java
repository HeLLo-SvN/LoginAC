package com.accenture.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 此模块的作用就是将从数据库查询出来的结果自动通过cache的方式存入Redis
 *
 * 使用RedisCacheManager需要注意
 * 1.对于存入Redis缓存的类型只支持<String,String>
 * 2.仅支持自动缓存单表查询的结果
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration
                = RedisCacheConfiguration
                .defaultCacheConfig()
                //设置缓存过期时间为60分钟
                .entryTtl(Duration.ofMinutes(60L))
                //不缓存空值
                .disableCachingNullValues()
                //设置key的序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                //设置value的序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .build();

    }

    /**
     *为了避免存进Redis中由于默认的JDK序列化,而造成的KEY的乱码
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();//设置key的序列化方式为String
    }

    /**
     *为了避免存进Redis中由于默认的JDK序列化,而造成的VALUE的乱码
     */
    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();//设置value的序列化方式为Json
    }

}
