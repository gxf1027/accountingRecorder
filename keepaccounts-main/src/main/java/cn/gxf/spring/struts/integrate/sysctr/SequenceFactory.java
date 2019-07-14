package cn.gxf.spring.struts.integrate.sysctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

@Service
public class SequenceFactory {
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	public static final String USER_ID_SEQ = "USER_ID_SEQUEUE";
	
	public long generate(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        return counter.incrementAndGet();
    }
	
	
	public long generate(String key, int increment) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        return counter.addAndGet(increment);
    }
	
	public void set(String key, long value){
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.set(value);
	}
}
