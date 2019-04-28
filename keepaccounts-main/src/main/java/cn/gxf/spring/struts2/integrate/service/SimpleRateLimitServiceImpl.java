package cn.gxf.spring.struts2.integrate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class SimpleRateLimitServiceImpl implements SimpleRateLimitService {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
		
		String key = String.format("hist:%s:%s", userId, actionKey);
		long nowTs = System.currentTimeMillis();
		long zcount = 0;
		
		List<Long> tries = redisTemplate.executePipelined(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				connection.zAdd(key.getBytes(), nowTs, ("V"+nowTs).getBytes());
				connection.zRemRangeByScore(key.getBytes(), 0, nowTs - period * 1000);
				connection.zCard(key.getBytes());
				connection.expire(key.getBytes(), period-1);
				return null; // MUST be null
			}
			
		});
		
//		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
//		zset.add(key, "V"+key, nowTs);
//		zset.removeRangeByScore(key, 0, nowTs - period * 1000);
//		System.out.println(tries);
		return tries.get(2) <= maxCount;
	}

}
