package cn.z.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.net.UnknownHostException;
import java.util.HashMap;

@Configuration
public class RedisConfig {
  // @Bean
  public RedisCacheManager jsonCacheManager(RedisConnectionFactory factory) {
    //初始化一个RedisCacheWriter
    RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
    //设置CacheManager的值序列化方式
                        // Jackson2JsonRedisSerializer serializer =new Jackson2JsonRedisSerializer(Emp.class);
    // HashMap可以做到通用
    Jackson2JsonRedisSerializer serializer =new Jackson2JsonRedisSerializer(HashMap.class);
    RedisSerializationContext.SerializationPair<Object> pair
            = RedisSerializationContext.SerializationPair.fromSerializer(serializer);
    RedisCacheConfiguration defaultCacheConfig
            =RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

    //初始化RedisCacheManager
    RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    return cacheManager;
  }
  
  // @Bean
  // @ConditionalOnMissingBean(name = "redisTemplate")
  // public RedisTemplate<Object, HashMap> hashMapRedisTemplate(
  //         RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
  //   RedisTemplate<Object, HashMap> template = new RedisTemplate<>();
  //   template.setConnectionFactory(redisConnectionFactory);
  //   Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(HashMap.class);
  //   template.setDefaultSerializer(serializer);
  //   return template;
  // }
}
