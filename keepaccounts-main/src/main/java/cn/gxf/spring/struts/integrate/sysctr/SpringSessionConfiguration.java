package cn.gxf.spring.struts.integrate.sysctr;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.session.Session;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionExpiredEvent;

import cn.gxf.spring.struts.integrate.cache.RedisKeysContants;
import cn.gxf.spring.struts.integrate.security.UserLogin;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
public class SpringSessionConfiguration {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private final String SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
	 
	@EventListener
	public void onSessionExpired(SessionExpiredEvent expiredEvent) {
        String sessionId = expiredEvent.getSessionId();
        Session s = expiredEvent.getSession();
        SecurityContextImpl securityContext= (SecurityContextImpl)s.getAttribute(SECURITY_CONTEXT);
        if (null != securityContext){
	        UserLogin user = (UserLogin)securityContext.getAuthentication().getPrincipal();
	        System.out.println("session expired: " + sessionId);
	        // session失效时在线人数-1
	        redisTemplate.opsForValue().setBit(RedisKeysContants.ONLINE_USERS_KEY, (long)user.getId(), false);
        }
    }
	
	@EventListener
	public void onSessionCreated(SessionCreatedEvent createdEvent) {
	        String sessionId = createdEvent.getSessionId();
	        System.out.println("session created: " + sessionId);
	 }
	
}
