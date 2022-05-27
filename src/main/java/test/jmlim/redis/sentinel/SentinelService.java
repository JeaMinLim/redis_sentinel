package test.jmlim.redis.sentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class SentinelService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getRedisValue(String KEY) {
        ValueOperations<String, String> RedisOperations = stringRedisTemplate.opsForValue();
        return KEY + " : " + RedisOperations.get(KEY);
    }
    public String setRedisValue(String KEY, String VALUE) {
        ValueOperations<String, String> RedisOperations = stringRedisTemplate.opsForValue();
        RedisOperations.set(KEY, VALUE);
        return getRedisValue(KEY);
    }
}
