package cn.gxf.spring.struts.integrate.security;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.gxf.spring.struts2.integrate.dao.UserDao;

public class LoginPostProcess implements Runnable{
	
    private Logger logger = LogManager.getLogger();

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
			logger.error(e.getMessage());
		}
		
	}
	
	
}