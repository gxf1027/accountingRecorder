package cn.gxf.spring.struts2.integrate.service;

import cn.gxf.spring.struts.integrate.security.UserLogin;

public interface RegisterUserService {
	public int registerUser(UserLogin user);
	public int isUserNameExists(String username);
}
