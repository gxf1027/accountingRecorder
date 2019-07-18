package cn.gxf.spring.struts.integrate.security;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

public class RedisUserCache implements UserCache{
	
	private final String prefix = "USER_";
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public UserDetails getUserFromCache(String username) {

		UserLogin userLogin = (UserLogin) redisTemplate.opsForValue().get(prefix+username);
		return userLogin;
	}

	@Override
	public void putUserInCache(UserDetails user) {
		
		redisTemplate.opsForValue().set(prefix+user.getUsername(), user, 3600, TimeUnit.SECONDS);
	}

	@Override
	public void removeUserFromCache(String username) {
		
		redisTemplate.delete(prefix+username);
	}
	
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
