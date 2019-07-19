package cn.gxf.spring.struts.integrate.security;

import java.util.Date;

import cn.gxf.spring.struts2.integrate.dao.UserDao;

public class LoginPostProcess implements Runnable{
	
	private UserDao userDao;
	private UserLogin user;
	private String ip;
	
	public LoginPostProcess(UserDao userDao, UserLogin user, String ip) {
		super();
		this.userDao = userDao;
		this.user = user;
		this.ip = ip;
	}

	@Override
	public void run() {
		try {
			userDao.resetUserAttemptLimit(user.getUsername());
	    	userDao.recordUserLoginInfo(user.getUsername(), new Date(), ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
