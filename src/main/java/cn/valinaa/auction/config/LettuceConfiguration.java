package cn.valinaa.auction.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;

/**
 * @author Valinaa
 * @Date 2022/8/17
 * @Description lettuce实现的redis配置类
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("all")
public class LettuceConfiguration extends CachingConfigurerSupport {
    
    private final LettuceConnectionFactory connectionFactory;
    
    /**
     * key 生成策略<p>
     * 不指定缓存的key时，使用keyGenerator生成Key。<p>
     * 自定义采用 类名+方法名+参数列表类型+参数值的哈希散列 作为key
     *
     * @return {@link KeyGenerator}
     * @see KeyGenerator
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            sb.append("&");
            for (Object obj : params) {
                if (obj != null) {
                    sb.append(obj.getClass().getName());
                    sb.append("&");
                    // 由于参数可能不同, hashCode肯定不一样, 缓存的key也需要不一样
                    try {
                        sb.append(new ObjectMapper().writeValueAsString(obj).hashCode());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            log.info("redis cache key str: " + sb);
            return sb.toString();
        };
    }
    
    /**
     * redis 模板配置
     *
     * @return {@link RedisTemplate}
     * @see RedisTemplate
     * @see Serializable
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂，源码默认
        template.setConnectionFactory(connectionFactory);
        // json序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // string序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 序列化key与value
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        
        return template;
    }
    
    @Bean
    @Override
    public CacheManager cacheManager() {
        return RedisCacheManager.RedisCacheManagerBuilder
                // Redis链接工厂
                .fromConnectionFactory(connectionFactory)
                // 缓存配置 通用配置  默认存储4小时
                .cacheDefaults(getCacheConfigurationWithTtl(Duration.ofHours(4)))
                // 配置同步修改或删除  put/evict
                .transactionAware()
                /*
                !对于不同的cacheName我们可以设置不同的过期时间
                .withCacheConfiguration("app:", getCacheConfigurationWithTtl(Duration.ofHours(5)))
                .withCacheConfiguration("user:", getCacheConfigurationWithTtl(Duration.ofHours(2)))
                */
                .build();
    }
    
    /**
     * 缓存的基本配置对象
     *
     * @param duration duration
     * @return {@link RedisCacheConfiguration}
     * @see RedisCacheConfiguration
     */
    private RedisCacheConfiguration getCacheConfigurationWithTtl(Duration duration) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                //! 设置key value的序列化方式
                // 设置key为String
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value 为自动转Json的Object
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                //! 不缓存null
                .disableCachingNullValues()
                //! 设置缓存的过期时间
                .entryTtl(duration);
    }
    
    /**
     * 缓存的异常处理
     *
     * @return {@link CacheErrorHandler}
     * @see CacheErrorHandler
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        //! 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(@NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }
            
            @Override
            
            public void handleCachePutError(@NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }
            
            @Override
            public void handleCacheEvictError(@NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }
            
            @Override
            public void handleCacheClearError(@NotNull RuntimeException e, @NotNull Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
    }
    
}
