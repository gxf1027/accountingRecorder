package cn.gxf.spring.struts.integrate.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import cn.gxf.spring.struts.integrate.cache.RedisKeysContants;
import cn.gxf.spring.struts.integrate.sysctr.KryoRedisSerializer;
import cn.gxf.spring.struts.integrate.sysctr.audit.AuditInfo;
import cn.gxf.spring.struts.integrate.sysctr.audit.AuditMsgSerivce;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

	private final static Logger logger = LoggerFactory.getLogger(KryoRedisSerializer.class);
	
	private String logoutSuccessUrl;
	
	private StringRedisTemplate redisTemplate;
	
	private AuditMsgSerivce auditService;
	
	private String audit;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("Authentication: " + authentication);
		System.out.println("on LogoutSuccess....");
		if (null != authentication){
			UserLogin user = (UserLogin) authentication.getPrincipal();
			if (null != user){
				UserLogin userLogin = (UserLogin)user;
				redisTemplate.opsForValue().setBit(RedisKeysContants.ONLINE_USERS_KEY, (long)userLogin.getId(), false);
				logger.info(String.format("%s Logout successfully", user.getUsername()));
				
				if (this.audit.equals("enabled")){
					try {
						auditService.sendAuditMsg(AuditInfo.LOGOUT, "µÇ³ö,("+user.getId()+", "+user.getUsername()+")", user.getId(), new Date());
					} catch (Exception e) {
						logger.warn(String.format("Logout successfully, but error happened when sending audit msg, %s", e.getMessage()));
					}
				}
			}
		}
		response.sendRedirect(this.logoutSuccessUrl);
	}
	
	
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}
	
	public String getLogoutSuccessUrl() {
		return logoutSuccessUrl;
	}
	
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setAuditService(AuditMsgSerivce auditService) {
		this.auditService = auditService;
	}
	
	public void setAudit(String audit) {
		this.audit = audit;
	}
}
