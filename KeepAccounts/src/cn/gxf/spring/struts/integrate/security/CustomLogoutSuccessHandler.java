package cn.gxf.spring.struts.integrate.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import cn.gxf.spring.struts.integrate.sysctr.KryoRedisSerializer;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

	private final static Logger logger = LoggerFactory.getLogger(KryoRedisSerializer.class);
	
	private String logoutSuccessUrl;
	

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("on LogoutSuccess....");
		UserLogin user = (UserLogin) authentication.getPrincipal();
		logger.info(String.format("%s Logout successfully", user.getUsername()));
		response.sendRedirect(this.logoutSuccessUrl);
	}
	
	
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}
	
	public String getLogoutSuccessUrl() {
		return logoutSuccessUrl;
	}

}
