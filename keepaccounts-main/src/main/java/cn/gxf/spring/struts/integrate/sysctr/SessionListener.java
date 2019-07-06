package cn.gxf.spring.struts.integrate.sysctr;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.gxf.spring.struts.integrate.cache.RedisKeysContants;
import cn.gxf.spring.struts.integrate.security.UserLogin;

public class SessionListener implements HttpSessionListener{

	private StringRedisTemplate redisTemplate;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("session created: " + se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
		HttpSession session = se.getSession();
		// session失效时在线人数-1
		UserLogin user = (UserLogin)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("session: " + session.getId() + " user: "+user.getUsername()+" invalidated");

		redisTemplate.opsForValue().setBit(RedisKeysContants.ONLINE_USERS_KEY, (long)user.getId(), false);
	}
	
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
}
