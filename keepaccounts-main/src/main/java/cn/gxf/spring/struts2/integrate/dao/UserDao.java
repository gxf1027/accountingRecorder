package cn.gxf.spring.struts2.integrate.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

import cn.gxf.spring.struts.integrate.security.UserLogin;

public interface UserDao {
	public Map<String, String> getUserEmail(List<String> useridList);
	
	// for UserDetailService
	public List<UserLogin> getUserLoginByName(String userName);
	public List<GrantedAuthority> getUserAutoritiesByName(String userName);
	public int getUserAttempts(String userName);
	public int resetUserAttemptLimit(String userName);
	public int decreaseUserAttempts(String userName);
	
	public void recordUserLoginInfo(String userName, Date lastLoginTime, String lastLoginIp);
}
