package test.jmlim.redis.sentinel;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class SentinelConfig {
    //@Value("${spring.redis.host}")
    //private String HOSTNAME;
    //@Value("${spring.redis.port}")
    //private int PORT;


    private String SENTINELHOST1 = "127.0.0.1";
    private String SENTINELHOST2 = "127.0.0.1";
    private String SENTINELHOST3 = "127.0.0.1";
    private int SENTINELPORT1 = 26379;
    private int SENTINELPORT2 = 26380;
    private int SENTINELPORT3 = 26381;

    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("RedisSentinel")
                .sentinel(SENTINELHOST1, SENTINELPORT1)
                .sentinel(SENTINELHOST2, SENTINELPORT2)
                .sentinel(SENTINELHOST3, SENTINELPORT3)
                ;
        return new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
        stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory());
        return stringRedisTemplate;
    }
}
