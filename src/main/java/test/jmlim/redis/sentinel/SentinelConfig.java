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

    @Value("${spring.redis.sentinel.master}")
    private String NAMEOFMASTER;
    @Value("${spring.redis.sentinel.nodes}")
    private String NODES;
    private String[] SENTINELHOST;
    private int[] SENTINELPORT;

    /* manually add Sentinel`s info
    private String NAMEOFMASTER = "RedisSentinel";
    private String SENTINELHOST1 = "127.0.0.1";
    private String SENTINELHOST2 = "127.0.0.1";
    private String SENTINELHOST3 = "127.0.0.1";
    private int SENTINELPORT1 = 26379;
    private int SENTINELPORT2 = 26380;
    private int SENTINELPORT3 = 26381;
     */

    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        // this is only need when Sentinel hostname and port is in application.yaml.
        //System.out.println(NODES);
        String[] _NODES = NODES.split(", ");
        int _length = _NODES.length;
        //System.out.println(_length);
        SENTINELHOST = new String[_length];
        SENTINELPORT = new int[_length];

        for(int i=0; i<_length; i++) {
            //System.out.println(_NODES[i]);
            String[] __NODES = _NODES[i].split(":");
            SENTINELHOST[i] = __NODES[0];
            SENTINELPORT[i] = Integer.parseInt(__NODES[1]);
            //System.out.println(SENTINELHOST[i] + " : "+ SENTINELPORT[i]);
        }
        // Until here!

        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(NAMEOFMASTER)
                .sentinel(SENTINELHOST[0], SENTINELPORT[0])
                .sentinel(SENTINELHOST[1], SENTINELPORT[1])
                .sentinel(SENTINELHOST[2], SENTINELPORT[2])
                ; // Add more .sentinel() if there is more then 3 sentinels.
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
